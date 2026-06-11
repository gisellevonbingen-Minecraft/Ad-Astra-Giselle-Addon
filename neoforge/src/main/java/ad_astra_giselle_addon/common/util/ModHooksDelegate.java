package ad_astra_giselle_addon.common.util;

import java.util.Optional;

import net.neoforged.fml.ModList;

public class ModHooksDelegate implements ModHooks.Delegate
{
	@Override
	public boolean isLoaded(String modid)
	{
		return ModList.get().isLoaded(modid);
	}

	@Override
	public Optional<String> getName(String modid)
	{
		return ModList.get().getModContainerById(modid).map(c -> c.getModInfo().getDisplayName());
	}

}
