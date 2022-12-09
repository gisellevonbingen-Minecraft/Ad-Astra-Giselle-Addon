package ad_astra_giselle_addon.common;

import ad_astra_giselle_addon.common.config.AddonConfigs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class AdAstraGiselleAddonFabric implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		AdAstraGiselleAddon.initializeCommon();
		AdAstraGiselleAddon.registerConfig(AddonConfigs.class);
		CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> AdAstraGiselleAddon.registerCommand(dispatcher::register));
	}

}
