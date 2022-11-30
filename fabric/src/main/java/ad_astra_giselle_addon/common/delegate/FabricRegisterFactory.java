package ad_astra_giselle_addon.common.delegate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unchecked")
public class FabricRegisterFactory implements DelegateRegistryFactory
{
	public static final FabricRegisterFactory INSTANCE = new FabricRegisterFactory();

	private final Map<ResourceKey<?>, InternalRegistry<?>> internals = new HashMap<>();

	private FabricRegisterFactory()
	{

	}

	@Override
	public <T> DelegateRegistry<T> get(ResourceKey<? extends Registry<T>> key)
	{
		return (DelegateRegistry<T>) this.internals.computeIfAbsent(key, k -> new InternalRegistry<>((ResourceKey<? extends Registry<T>>) k));
	}

	private class InternalRegistry<T> implements DelegateRegistry<T>
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

	}

}
