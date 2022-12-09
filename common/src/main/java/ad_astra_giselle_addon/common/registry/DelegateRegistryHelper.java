package ad_astra_giselle_addon.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class DelegateRegistryHelper
{
	private static final Delegate DELEGATE =  new DelegateRegistryHelperDelegate();
	
	public static <T> DelegateRegistry<T> get(ResourceKey<? extends Registry<T>> key)
	{
		return DELEGATE.get(key);
	}

	public static interface Delegate
	{
		<T> DelegateRegistry<T> get(ResourceKey<? extends Registry<T>> key);
	}

}
