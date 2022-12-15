package ad_astra_giselle_addon.common.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import ad_astra_giselle_addon.common.util.TriFunction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class ObjectRegistryCollection<T>
{
	private final String modid;
	private final ResourceKey<? extends Registry<T>> key;
	private final Set<ObjectRegistryHolder<T>> objects;
	private final Set<ObjectRegistryHolder<T>> readonlyObjects;

	public ObjectRegistryCollection(String modid, ResourceKey<? extends Registry<T>> key)
	{
		this.modid = modid;
		this.key = key;
		this.objects = new HashSet<>();
		this.readonlyObjects = Collections.unmodifiableSet(this.objects);
	}

	public static <T> ObjectRegistryCollection<T> create(String modid, ResourceKey<? extends Registry<T>> key)
	{
		return new ObjectRegistryCollection<>(modid, key);
	}

	public void register()
	{
		ObjectRegistry<T> registry = ObjectRegistry.get(this.getKey());

		for (ObjectRegistryHolder<T> object : this.getObjects())
		{
			object.register(registry);
		}

	}

	@SuppressWarnings("unchecked")
	protected <I extends T, HOLDER extends ObjectRegistryHolder<? extends I>> HOLDER add(String name, Supplier<? extends I> initializer, TriFunction<ResourceLocation, Supplier<? extends I>, ResourceKey<? extends Registry<?>>, HOLDER> func)
	{
		HOLDER holder = func.apply(new ResourceLocation(this.getModid(), name), initializer, this.getKey());
		this.objects.add((ObjectRegistryHolder<T>) holder);
		return holder;
	}

	public <I extends T> ObjectRegistryHolder<I> add(String name, Supplier<? extends I> initializer)
	{
		return this.add(name, initializer, ObjectRegistryHolder<I>::new);
	}

	public String getModid()
	{
		return this.modid;
	}

	public ResourceKey<? extends Registry<T>> getKey()
	{
		return this.key;
	}

	public Collection<ObjectRegistryHolder<T>> getObjects()
	{
		return this.readonlyObjects;
	}

	public Stream<T> stream()
	{
		return this.getObjects().stream().map(ObjectRegistryHolder<T>::get);
	}

	public Collection<T> getValues()
	{
		return this.stream().toList();
	}

}
