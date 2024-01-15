package ad_astra_giselle_addon.client.overlay;

import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
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
		else if (SpaceSuitItem.hasFullSet(player) && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpaceSuitItem)
		{
			OxygenChargerUtils.getExtractableStoredRatio(player).ifPresent(ratio ->
			{
				Font font = minecraft.font;
				Component component = Component.translatable(OXYGENCAN_DESCRIPTION_ID).append(": ").append(Component.empty().append(TranslationUtils.formatPercent(ratio)).withStyle(s -> s.withColor(Mth.hsvToRgb((float) (ratio / 3.0F), 1.0F, 1.0F))));
				int textureWidth = 62;
				int textureHeight = 52;
				int width = font.width(component);
				int x = (int) (AdAstraConfigClient.oxygenBarX + (textureWidth - width) / 2.0F);
				int y = AdAstraConfigClient.oxygenBarY + textureHeight + font.lineHeight + 3;
				guiGraphics.drawString(font, component, Math.max(x, 0), y, 0xFFFFFF);
			});
		}

	}

}
