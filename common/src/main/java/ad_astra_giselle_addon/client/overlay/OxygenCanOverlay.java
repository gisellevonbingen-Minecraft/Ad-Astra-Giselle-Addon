package ad_astra_giselle_addon.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.oxygen.OxygenStorageUtils;
import ad_astra_giselle_addon.common.event.EventSystem;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.client.screen.PlayerOverlayScreen;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class OxygenCanOverlay
{
	public static final EventSystem<ShouldRenderEventListener> SHOULD_RENDER_EVENT = new EventSystem<>();

	public static final ResourceLocation OXYGEN_TANK_EMPTY_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/overlay/oxygen_tank_empty.png");
	public static final ResourceLocation OXYGEN_TANK_FULL_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/overlay/oxygen_tank_full.png");

	public static final String OXYGENCAN_DESCRIPTION_ID = Util.makeDescriptionId("item", AddonItems.OXYGEN_CAN.getId());
	public static final Component INFINITY_TEXT = Component.translatable(TranslationUtils.CREATIVE_OXYGEN_INFINITY);

	static
	{
		SHOULD_RENDER_EVENT.register(player -> EnchantmentHelper.getEnchantmentLevel(AddonEnchantments.OXYGEN_PROOF.get(), player) > 0);
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

	public static void renderHud(PoseStack poseStack, float partialTick)
	{
		Minecraft minecraft = Minecraft.getInstance();
		LocalPlayer player = minecraft.player;

		if (player.isSpectator() || minecraft.options.renderDebug)
		{
			return;
		}
		else if (PlayerOverlayScreen.shouldRenderOxygen)
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
				int textWidth = font.width(component);
				float textX = 5 + AdAstraConfig.oxygenBarXOffset + (textureWidth - textWidth) / 2.0F;
				float textY = 25 + AdAstraConfig.oxygenBarYOffset + textureHeight + font.lineHeight + 3;
				poseStack.pushPose();
				poseStack.scale(AdAstraConfig.oxygenBarScale, AdAstraConfig.oxygenBarScale, AdAstraConfig.oxygenBarScale);
				font.drawShadow(poseStack, component, Math.max(textX, 0.0F), textY, 0xFFFFFF);
				poseStack.popPose();
			});
		}
		else if (shouldRender(player))
		{
			OxygenStorageUtils.getStoredRatio(player).ifPresent(ratio ->
			{
				renderOxygenCanTank(poseStack, minecraft, ratio);
			});

		}

	}

	public static void renderOxygenCanTank(PoseStack poseStack, Minecraft minecraft, double oxygenRatio)
	{
		var normalizedRatio = Math.min(oxygenRatio, 1.0D);
		poseStack.pushPose();
		poseStack.scale(AdAstraConfig.oxygenBarScale, AdAstraConfig.oxygenBarScale, AdAstraConfig.oxygenBarScale);

		Rect2i rect = PlayerOverlayScreen.getOxygenTankUnscaledRect(minecraft);
		GuiUtil.drawVerticalReverse(poseStack, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), OXYGEN_TANK_EMPTY_TEXTURE, normalizedRatio);
		GuiUtil.drawVertical(poseStack, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), OXYGEN_TANK_FULL_TEXTURE, normalizedRatio);

		// Oxygen text
		Font font = minecraft.font;
		Component text = getOxygenComponent(oxygenRatio);
		int textWidth = font.width(text);
		float textX = rect.getX() + (rect.getWidth() - textWidth) / 2.0F;
		float textY = rect.getY() + rect.getHeight() + 3;
		font.drawShadow(poseStack, text, Math.max(textX, 0.0F), textY, 0xFFFFFF);
		poseStack.popPose();
	}

	@FunctionalInterface
	public static interface ShouldRenderEventListener
	{
		boolean shouldRender(LocalPlayer player);
	}

}
