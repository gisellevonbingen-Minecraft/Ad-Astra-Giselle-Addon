package ad_astra_giselle_addon.common;

import ad_astra_giselle_addon.common.AddonKeyBindings.Key;
import ad_astra_giselle_addon.common.content.jet_suit.JetSuitHandler;
import net.minecraft.world.entity.player.Player;

public class AddonKeyHandler
{
	public static void onKeyChanged(Player player, AddonKeyBindings.Key key, boolean isDown)
	{
		if (isDown && (key == Key.JET_SUIT_POWER || key == Key.JET_SUIT_HOVER))
		{
			JetSuitHandler.toggleAndSendMessage(player, key);
		}

	}

	private AddonKeyHandler()
	{

	}

}
