package ad_astra_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.compat.techreborn.TechRebornCompat;

public class FabricCompatibleManager
{
	public static final List<CompatibleMod> MODS;
	public static final TechRebornCompat TECH_REBORN;

	static
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(TECH_REBORN = new TechRebornCompat());

		MODS = Collections.unmodifiableList(mods);
	}

}
