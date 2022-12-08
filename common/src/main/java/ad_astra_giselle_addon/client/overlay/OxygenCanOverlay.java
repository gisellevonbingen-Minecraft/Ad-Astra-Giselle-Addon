package ad_astra_giselle_addon.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.ad_astra.client.screens.PlayerOverlayScreen;
import earth.terrarium.ad_astra.config.AdAstraConfig;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class OxygenCanOverlay
{
	public static final String OXYGENCAN_DESCRIPTION_ID = Util.makeDescriptionId("item", AddonItems.OXYGEN_CAN.getId());

	public static void renderHud(PoseStack poseStack, float partialTick)
	{
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;

		if (player.isSpectator())
		{
			return;
		}
		else if (PlayerOverlayScreen.shouldRenderOxygen && !minecraft.options.renderDebug)
		{
			OxygenChargerUtils.getExtractableStoredRatio(player).ifPresent(ratio ->
			{
				Font font = minecraft.font;
				Component component = Component.translatable(OXYGENCAN_DESCRIPTION_ID).append(": ").append(TranslationUtils.formatPercent(ratio));
				int textureWidth = 62;
				int textureHeight = 52;
				int width = font.width(component);
				float x = 5 + AdAstraConfig.oxygenBarXOffset + (textureWidth - width) / 2.0F;
				float y = 25 + AdAstraConfig.oxygenBarYOffset + textureHeight + font.lineHeight + 3;
				font.drawShadow(poseStack, component, Math.max(x, 0), y, 0xFFFFFF);
			});
		}

	}

}
