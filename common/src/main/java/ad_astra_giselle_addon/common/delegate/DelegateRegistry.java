package ad_astra_giselle_addon.common.delegate;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;

public interface DelegateRegistry<T>
{
	Supplier<T> register(ResourceLocation id, Supplier<? extends T> initializer);

	ResourceLocation getId(T value);

	T getValue(ResourceLocation id);

	Iterable<T> getValues();
}
