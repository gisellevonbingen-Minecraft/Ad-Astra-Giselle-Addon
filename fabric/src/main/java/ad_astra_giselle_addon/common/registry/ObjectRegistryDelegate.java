package ad_astra_giselle_addon.common.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unchecked")
public class ObjectRegistryDelegate implements ObjectRegistry.Delegate
{
	private final Map<ResourceKey<?>, InternalRegistry<?>> internals = new HashMap<>();

	@Override
	public <T> ObjectRegistry<T> get(ResourceKey<? extends Registry<T>> key)
	{
		return (ObjectRegistry<T>) this.internals.computeIfAbsent(key, k -> new InternalRegistry<>((ResourceKey<? extends Registry<T>>) k));
	}

	private class InternalRegistry<T> extends ObjectRegistry<T>
	{
		private final ResourceKey<? extends Registry<T>> key;

		private InternalRegistry(ResourceKey<? extends Registry<T>> key)
		{
			this.key = key;
		}

		@Override
		public Supplier<T> register(ResourceLocation id, Supplier<? extends T> initializer)
		{
			Registry<T> registry = this.getRegistry();
			T object = Registry.register(registry, id, initializer.get());
			return () -> object;
		}

		private Registry<T> getRegistry()
		{
			return (Registry<T>) Registry.REGISTRY.get(this.key.location());
		}

		@Override
		public ResourceLocation getId(T value)
		{
			return this.getRegistry().getKey(value);
		}

		@Override
		public T getValue(ResourceLocation id)
		{
			return this.getRegistry().get(id);
		}

		@Override
		public Iterable<T> getValues()
		{
			return this.getRegistry();
		}

	}

}
