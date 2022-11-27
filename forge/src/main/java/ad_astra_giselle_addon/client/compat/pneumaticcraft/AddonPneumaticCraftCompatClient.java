package ad_astra_giselle_addon.client.compat.pneumaticcraft;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AddonPneumaticCraftCompatClient
{
	public AddonPneumaticCraftCompatClient()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		fml_bus.addListener(this::clientSetup);
	}

	public void clientSetup(FMLClientSetupEvent event)
	{
		AddonClientUpgradeHandlers.register();
	}

}
