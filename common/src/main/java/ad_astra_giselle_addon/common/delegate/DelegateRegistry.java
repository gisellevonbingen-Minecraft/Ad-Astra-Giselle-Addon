package ad_astra_giselle_addon.common.delegate;

import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface DelegateRegistry<T>
{
	public static <T> DelegateRegistry<T> get(ResourceKey<? extends Registry<T>> key)
	{
		return AdAstraGiselleAddon.delegate().getRegistryHelper().getRegistry(key);
	}

	Supplier<T> register(ResourceLocation id, Supplier<? extends T> initializer);

	ResourceLocation getId(T value);

	T getValue(ResourceLocation id);

	Iterable<T> getValues();

	public default Stream<T> stream()
	{
		return StreamSupport.stream(this.getValues().spliterator(), false);
	}

}
