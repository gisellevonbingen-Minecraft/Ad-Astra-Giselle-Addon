package ad_astra_giselle_addon.common;

import ad_astra_giselle_addon.client.AdAstraGiselleAddonClientForge;
import ad_astra_giselle_addon.common.config.AddonForgeConfigs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(AdAstraGiselleAddon.MOD_ID)
public class AdAstraGiselleAddonForge
{
	public AdAstraGiselleAddonForge()
	{
		AdAstraGiselleAddon.initializeCommon();
		AdAstraGiselleAddon.registerConfig(AddonForgeConfigs.class);

		IEventBus forge_bus = NeoForge.EVENT_BUS;
		forge_bus.addListener((RegisterCommandsEvent e) -> AdAstraGiselleAddon.registerCommand(e.getDispatcher()::register));

		if (FMLEnvironment.dist.isClient())
		{
			new AdAstraGiselleAddonClientForge();
		}

	}

}
