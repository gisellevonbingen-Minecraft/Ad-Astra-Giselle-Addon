package ad_astra_giselle_addon.common.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompatibleManager
{
	private static final Delegate DELEGATE = new CompatibleManagerDelegate(); 
			
	public final List<CompatibleMod> mods;
	public final JeiCompat JEI;
	public final ReiCompat REI;

	public CompatibleManager()
	{
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(this.JEI = new JeiCompat());
		mods.add(this.REI = new ReiCompat());
		mods.addAll(DELEGATE.getMods());
		this.mods = Collections.unmodifiableList(mods);
	}

	public void tryLoad()
	{
		this.mods.forEach(c -> c.tryLoad());
	}

	public static interface Delegate
	{
		List<CompatibleMod> getMods();
	}
	
}
