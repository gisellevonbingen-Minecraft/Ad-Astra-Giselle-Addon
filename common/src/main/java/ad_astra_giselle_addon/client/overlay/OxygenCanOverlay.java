package ad_astra_giselle_addon.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.oxygen.OxygenStorageUtils;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.screens.player.OverlayScreen;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class OxygenCanOverlay
{
	public static final String OXYGENCAN_DESCRIPTION_ID = Util.makeDescriptionId("item", AddonItems.OXYGEN_CAN.getId());
	public static final Component INFINITY_TEXT = Component.translatable(TranslationUtils.CREATIVE_OXYGEN_INFINITY);

	public static Component getOxygenComponent(double ratio)
	{
		Component text = getRatioText(ratio);
		return Component.translatable(OXYGENCAN_DESCRIPTION_ID).append(": ").append(text).withStyle(s -> s.withColor(Mth.hsvToRgb((float) (ratio / 3.0F), 1.0F, 1.0F)));
	}

	public static Component getRatioText(double ratio)
	{
		return ratio == Double.POSITIVE_INFINITY ? INFINITY_TEXT : TranslationUtils.formatPercent(ratio);
	}

	public static void renderHud(GuiGraphics guiGraphics, float partialTick)
	{
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;

		if (player == null || player.isSpectator())
		{
			return;
		}
		else if (minecraft.options.renderDebug)
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
				Component component = getOxygenComponent(ratio);
				int textureWidth = 62;
				int textureHeight = 52;
				int width = font.width(component);
				int x = (int) (AdAstraConfigClient.oxygenBarX + (textureWidth - width) / 2.0F);
				int y = AdAstraConfigClient.oxygenBarY + textureHeight + font.lineHeight + 3;
				guiGraphics.drawString(font, component, Math.max(x, 0), y, 0xFFFFFF);
			});
		}
		else if (EnchantmentHelper.getEnchantmentLevel(AddonEnchantments.OXYGEN_PROOF.get(), player) > 0)
		{
			OxygenStorageUtils.getStoredRatio(player).ifPresent(ratio ->
			{
				renderOxygenCanTank(guiGraphics, minecraft, ratio);
			});

		}

	}

	public static void renderOxygenCanTank(GuiGraphics graphics, Minecraft minecraft, double oxygenRatio)
	{
		int barHeight = (int) (Math.min(oxygenRatio, 1.0D) * 52);

		int x = AdAstraConfigClient.oxygenBarX;
		int y = AdAstraConfigClient.oxygenBarY;
		float scale = AdAstraConfigClient.oxygenBarScale;

		PoseStack poseStack = graphics.pose();
		poseStack.pushPose();
		poseStack.scale(scale, scale, scale);
		graphics.blit(OverlayScreen.OXYGEN_TANK_EMPTY, x, y, 0, 0, 62, 52, 62, 52);
		graphics.blit(OverlayScreen.OXYGEN_TANK, x, y + 52 - barHeight, 0, 52 - barHeight, 62, barHeight, 62, 52);

		var font = minecraft.font;
		var text = getOxygenComponent(oxygenRatio);
		int textWidth = font.width(text);
		graphics.drawString(font, text, (int) (x + (62 - textWidth) / 2f), y + 52 + 3, 0xFFFFFF);
		poseStack.popPose();
	}

}
