package ad_astra_giselle_addon.common.util;

import java.util.Optional;

import net.fabricmc.loader.api.FabricLoader;

public class ModHooksDelegate implements ModHooks.Delegate
{
	@Override
	public boolean isLoaded(String modid)
	{
		return FabricLoader.getInstance().isModLoaded(modid);
	}

	@Override
	public Optional<String> getName(String modid)
	{
		return FabricLoader.getInstance().getModContainer(modid).map(c -> c.getMetadata().getName());
	}

}
