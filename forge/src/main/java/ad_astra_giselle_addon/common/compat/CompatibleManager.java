package ad_astra_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.compat.curios.CuriosCompat;
import ad_astra_giselle_addon.common.compat.jei.JeiCompat;
import ad_astra_giselle_addon.common.compat.mekanism.MekanismCompat;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.PneumaticCraftCompat;

public class CompatibleManager
{
	public static final List<CompatibleMod> MODS;
	public static final JeiCompat JEI;
	public static final CuriosCompat CURIOS;
	public static final MekanismCompat MEKANISM;
	public static final PneumaticCraftCompat PNEUMATICCRAFT;

	static
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(JEI = new JeiCompat());
		mods.add(CURIOS = new CuriosCompat());
		mods.add(MEKANISM = new MekanismCompat());
		mods.add(PNEUMATICCRAFT = new PneumaticCraftCompat());

		for (CompatibleMod mod : mods)
		{
			mod.tryLoad();
		}

		MODS = Collections.unmodifiableList(mods);
	}

	public static void visit()
	{

	}

}
