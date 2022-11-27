package ad_astra_giselle_addon.common;

import ad_astra_giselle_addon.common.command.AddonCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListenerCommand
{
	@SubscribeEvent
	public static void onServerStarting(RegisterCommandsEvent e)
	{
		e.getDispatcher().register(AddonCommand.builder());
	}

}
