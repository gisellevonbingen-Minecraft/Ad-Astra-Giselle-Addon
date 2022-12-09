package ad_astra_giselle_addon.common.util;

import java.util.Optional;

public class ModHooks
{
	private static final Delegate DELEGATE = new ModHooksDelegate();

	public static boolean isLoaded(String modid)
	{
		return DELEGATE.isLoaded(modid);
	}

	public static String getName(String modid)
	{
		return DELEGATE.getName(modid).orElseGet(() -> getFallbackName(modid));
	}

	protected static String getFallbackName(String modid)
	{
		return "Unknown ModId: " + modid;
	}

	public static interface Delegate
	{
		boolean isLoaded(String modid);

		Optional<String> getName(String modid);
	}

}
