package ad_astra_giselle_addon.client.compat.pneumaticcraft;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class AddonPneumaticCraftCompatClient
{
	public AddonPneumaticCraftCompatClient()
	{
		IEventBus fml_bus = ModLoadingContext.get().getActiveContainer().getEventBus();
		fml_bus.addListener(this::clientSetup);
	}

	public void clientSetup(FMLClientSetupEvent event)
	{
		AddonClientUpgradeHandlers.register();
	}

}
