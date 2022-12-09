package ad_astra_giselle_addon.common.registry;

import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public abstract class ObjectRegistry<T>
{
	private static final Delegate DELEGATE = new ObjectRegistryDelegate();

	public static <T> ObjectRegistry<T> get(ResourceKey<? extends Registry<T>> key)
	{
		return DELEGATE.get(key);
	}

	public abstract Supplier<T> register(ResourceLocation id, Supplier<? extends T> initializer);

	public abstract ResourceLocation getId(T value);

	public abstract T getValue(ResourceLocation id);

	public abstract Iterable<T> getValues();

	public Stream<T> stream()
	{
		return StreamSupport.stream(this.getValues().spliterator(), false);
	}

	public static interface Delegate
	{
		<T> ObjectRegistry<T> get(ResourceKey<? extends Registry<T>> key);
	}

}
