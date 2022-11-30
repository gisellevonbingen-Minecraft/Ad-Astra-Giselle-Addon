package ad_astra_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.delegate.PlatformCommonDelegate;

public class CompatibleManager
{
	public final List<CompatibleMod> mods;

	public CompatibleManager(Collection<CompatibleMod> addons)
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.addAll(addons);
		this.mods = Collections.unmodifiableList(mods);
	}

	public void tryLoad(PlatformCommonDelegate delegate)
	{
		this.mods.forEach(c -> c.tryLoad(delegate));
	}

}
