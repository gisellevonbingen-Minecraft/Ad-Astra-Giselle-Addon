package ad_astra_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompatibleManager
{
	private static final Delegate DELEGATE = new CompatibleManagerDelegate();

	public static final List<CompatibleMod> COMMON_MODS;
	public static final JeiCompat JEI;
	public static final ReiCompat REI;

	static
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(JEI = new JeiCompat());
		mods.add(REI = new ReiCompat());

		COMMON_MODS = Collections.unmodifiableList(mods);
	}

	public final List<CompatibleMod> all_mods;
	public final List<CompatibleMod> loaded_mods;

	public CompatibleManager()
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.addAll(COMMON_MODS);
		mods.addAll(DELEGATE.getMods());
		this.all_mods = Collections.unmodifiableList(mods);
		this.all_mods.forEach(CompatibleMod::tryLoad);
		this.loaded_mods = this.all_mods.stream().filter(CompatibleMod::isLoaded).toList();
		
	}

	public static interface Delegate
	{
		List<CompatibleMod> getMods();
	}

}
