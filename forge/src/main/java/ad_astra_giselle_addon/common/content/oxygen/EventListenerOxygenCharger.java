package ad_astra_giselle_addon.common.content.oxygen;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListenerOxygenCharger
{
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent e)
	{
		Player player = e.player;

		if (!player.getLevel().isClientSide())
		{
			OxygenChargerUtils.streamExtractable(player, 1L, null).forEach(c ->
			{
				OxygenChargerUtils.distributeToItems(player, c);
			});

		}

	}

}
