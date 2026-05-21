package ad_astra_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.compat.techreborn.TechRebornCompat;
import ad_astra_giselle_addon.common.compat.trinkets.TrinketsCompat;

public class CompatibleManagerDelegate implements CompatibleManager.Delegate
{
	public static final List<CompatibleMod> MODS;
	public static final TechRebornCompat TECH_REBORN;
	public static final TrinketsCompat TRINKETS;

	static
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(TECH_REBORN = new TechRebornCompat());
		mods.add(TRINKETS = new TrinketsCompat());

		MODS = Collections.unmodifiableList(mods);
	}

	@Override
	public List<CompatibleMod> getMods()
	{
		return MODS;
	}

}
