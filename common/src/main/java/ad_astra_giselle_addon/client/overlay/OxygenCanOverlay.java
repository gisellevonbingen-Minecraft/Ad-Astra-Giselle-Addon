package ad_astra_giselle_addon.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.oxygen.OxygenStorageUtils;
import ad_astra_giselle_addon.common.event.EventSystem;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.screens.player.OverlayScreen;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class OxygenCanOverlay
{
	public static final EventSystem<ShouldRenderEventListener> SHOULD_RENDER_EVENT = new EventSystem<>();

	public static final String OXYGENCAN_DESCRIPTION_ID = Util.makeDescriptionId("item", AddonItems.OXYGEN_CAN.getId());
	public static final Component INFINITY_TEXT = Component.translatable(TranslationUtils.CREATIVE_OXYGEN_INFINITY);

	static
	{
		SHOULD_RENDER_EVENT.register(player ->
		{
			Reference<Enchantment> holderOrThrow = player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(AddonEnchantments.OXYGEN_PROOF);
			return EnchantmentHelper.getEnchantmentLevel(holderOrThrow, player) > 0;
		});
	}

	public static Component getOxygenComponent(double ratio)
	{
		Component text = getRatioText(ratio);
		return Component.translatable(OXYGENCAN_DESCRIPTION_ID).append(": ").append(text).withStyle(s -> s.withColor(Mth.hsvToRgb((float) (ratio / 3.0F), 1.0F, 1.0F)));
	}

	public static Component getRatioText(double ratio)
	{
		return ratio == Double.POSITIVE_INFINITY ? INFINITY_TEXT : TranslationUtils.formatPercent(ratio);
	}

	public static boolean shouldRender(LocalPlayer player)
	{
		for (var listener : SHOULD_RENDER_EVENT.getListeners())
		{
			if (listener.shouldRender(player))
			{
				return true;
			}

		}

		return false;
	}

	public static void renderHud(GuiGraphics guiGraphics, DeltaTracker partialTick)
	{
		Minecraft minecraft = Minecraft.getInstance();
		LocalPlayer player = minecraft.player;

		if (player == null || player.isSpectator())
		{
			return;
		}
		else if (minecraft.getDebugOverlay().showDebugScreen())
		{
			return;
		}
		else if (SpaceSuitItem.hasFullSet(player) && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpaceSuitItem)
		{
			OxygenStorageUtils.getStoredRatio(OxygenStorageUtils.stream(player).filter(item ->
			{
				IOxygenCharger oxygenCharger = OxygenChargerUtils.get(item);
				return oxygenCharger != null && oxygenCharger.getChargeMode().contains(EquipmentSlot.CHEST);
			})).ifPresent(ratio ->
			{
				Font font = minecraft.font;
				Component text = getOxygenComponent(ratio);
				int textureWidth = 62;
				int textureHeight = 52;
				int textWidth = font.width(text);
				int textX = (int) (AdAstraConfigClient.oxygenBarX + (textureWidth - textWidth) / 2.0F);
				int textY = AdAstraConfigClient.oxygenBarY + textureHeight + font.lineHeight + 3;
				guiGraphics.pose().pushPose();
				guiGraphics.pose().scale(AdAstraConfigClient.oxygenBarScale, AdAstraConfigClient.oxygenBarScale, AdAstraConfigClient.oxygenBarScale);
				guiGraphics.drawString(font, text, Math.max(textX, 0), textY, 0xFFFFFF);
				guiGraphics.pose().popPose();
			});
		}
		else if (shouldRender(player))
		{
			OxygenStorageUtils.getStoredRatio(player).ifPresent(ratio ->
			{
				renderOxygenCanTank(guiGraphics, minecraft, ratio);
			});

		}

	}

	public static void renderOxygenCanTank(GuiGraphics graphics, Minecraft minecraft, double oxygenRatio)
	{
		int textureWidth = 62;
		int textureHeight = 52;
		int barHeight = (int) (Math.min(oxygenRatio, 1.0D) * textureHeight);

		int x = AdAstraConfigClient.oxygenBarX;
		int y = AdAstraConfigClient.oxygenBarY;
		float scale = AdAstraConfigClient.oxygenBarScale;

		PoseStack poseStack = graphics.pose();
		poseStack.pushPose();
		poseStack.scale(scale, scale, scale);
		graphics.blitSprite(OverlayScreen.OXYGEN_TANK_EMPTY, x, y, textureWidth, textureHeight);
		graphics.blit(OverlayScreen.OXYGEN_TANK, x, y + textureHeight - barHeight, 0, textureHeight - barHeight, textureWidth, barHeight, textureWidth, textureHeight);

		var font = minecraft.font;
		var text = getOxygenComponent(oxygenRatio);
		int textWidth = font.width(text);
		int textX = (int) (x + (textureWidth - textWidth) / 2.0F);
		int textY = y + textureHeight + 3;
		graphics.drawString(font, text, Math.max(textX, 0), textY, 0xFFFFFF);
		poseStack.popPose();
	}

	@FunctionalInterface
	public static interface ShouldRenderEventListener
	{
		boolean shouldRender(LocalPlayer player);
	}

}
