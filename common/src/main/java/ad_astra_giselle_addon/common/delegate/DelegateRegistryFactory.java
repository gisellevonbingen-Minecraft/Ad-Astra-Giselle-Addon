package ad_astra_giselle_addon.common.delegate;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

@FunctionalInterface
public interface DelegateRegistryFactory
{
	<T> DelegateRegistry<T> get(ResourceKey<? extends Registry<T>> key);
}
