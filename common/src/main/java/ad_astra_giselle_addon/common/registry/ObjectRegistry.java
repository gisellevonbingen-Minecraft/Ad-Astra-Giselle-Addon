package ad_astra_giselle_addon.common.registry;

import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public abstract class ObjectRegistry<T>
{
	private static final Delegate DELEGATE = new ObjectRegistryDelegate();

	public static <T> ObjectRegistry<T> get(ResourceKey<? extends Registry<T>> key)
	{
		return DELEGATE.get(key);
	}

	private final ResourceKey<? extends Registry<T>> key;

	protected ObjectRegistry(ResourceKey<? extends Registry<T>> key)
	{
		this.key = key;
	}

	public abstract Supplier<T> register(ResourceLocation id, Supplier<? extends T> initializer);

	public ResourceKey<? extends Registry<T>> getKey()
	{
		return this.key;
	}

	@SuppressWarnings("unchecked")
	public Registry<T> getRegistry()
	{
		return (Registry<T>) BuiltInRegistries.REGISTRY.get(this.getKey().location());
	}

	public Set<Entry<ResourceKey<T>, T>> getEntries()
	{
		return this.getRegistry().entrySet();
	}

	public ResourceLocation getId(T value)
	{
		return this.getRegistry().getKey(value);
	}

	public T getValue(ResourceLocation id)
	{
		return this.getRegistry().get(id);
	}

	public Iterable<T> getValues()
	{
		return this.getRegistry();
	}

	public Stream<T> stream()
	{
		return StreamSupport.stream(this.getValues().spliterator(), false);
	}

	public static interface Delegate
	{
		<T> ObjectRegistry<T> get(ResourceKey<? extends Registry<T>> key);
	}

}
