package ad_astra_giselle_addon.common.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ObjectRegistryDelegate implements ObjectRegistry.Delegate
{
	private final Map<ResourceKey<?>, InternalRegistry<?>> internals = new HashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public <T> ObjectRegistry<T> get(ResourceKey<? extends Registry<T>> key)
	{
		return (ObjectRegistry<T>) this.internals.computeIfAbsent(key, k -> new InternalRegistry<>((ResourceKey<? extends Registry<T>>) k));
	}

	private class InternalRegistry<T> extends ObjectRegistry<T>
	{
		private final Map<String, DeferredRegister<T>> registries;

		private InternalRegistry(ResourceKey<? extends Registry<T>> key)
		{
			super(key);
			this.registries = new HashMap<>();
		}

		@Override
		public Supplier<T> register(ResourceLocation id, Supplier<? extends T> initializer)
		{
			DeferredRegister<T> register = this.registries.computeIfAbsent(id.getNamespace(), this::createDeferredRegister);
			return register.register(id.getPath(), initializer);
		}

		private DeferredRegister<T> createDeferredRegister(String modid)
		{
			DeferredRegister<T> register = DeferredRegister.create(this.getKey(), modid);
			register.register(ModLoadingContext.get().getActiveContainer().getEventBus());
			return register;
		}

	}

}
