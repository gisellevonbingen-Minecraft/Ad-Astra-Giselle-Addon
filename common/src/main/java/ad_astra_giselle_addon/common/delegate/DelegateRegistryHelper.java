package ad_astra_giselle_addon.common.delegate;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface DelegateRegistryHelper
{
	<T> DelegateRegistry<T> getRegistry(ResourceKey<? extends Registry<T>> key);
}
