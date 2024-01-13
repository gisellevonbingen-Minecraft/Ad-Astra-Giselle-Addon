package ad_astra_giselle_addon.client.overlay;

import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class OxygenCanOverlay
{
	public static final String OXYGENCAN_DESCRIPTION_ID = Util.makeDescriptionId("item", AddonItems.OXYGEN_CAN.getId());

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
		else
		{
			OxygenChargerUtils.getExtractableStoredRatio(player).ifPresent(ratio ->
			{
				Font font = minecraft.font;
				Component component = Component.translatable(OXYGENCAN_DESCRIPTION_ID).append(": ").append(TranslationUtils.formatPercent(ratio));
				int textureWidth = 62;
				int textureHeight = 52;
				int width = font.width(component);
				int x = 5 + (int) (AdAstraConfigClient.oxygenBarX + (textureWidth - width) / 2.0F);
				int y = 25 + AdAstraConfigClient.oxygenBarY + textureHeight + font.lineHeight + 3;
				guiGraphics.drawString(font, component, Math.max(x, 0), y, 0xFFFFFF);
			});
		}

	}

}
