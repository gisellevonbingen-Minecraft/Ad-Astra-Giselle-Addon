package ad_astra_giselle_addon.common.delegate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryManager;

@SuppressWarnings("unchecked")
public class ForgeRegisterFactory implements DelegateRegistryFactory
{
	public static final ForgeRegisterFactory INSTANCE = new ForgeRegisterFactory();

	private final Map<ResourceKey<?>, InternalRegistry<?>> internals = new HashMap<>();

	private ForgeRegisterFactory()
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
		private final Map<String, DeferredRegister<T>> registries;

		private InternalRegistry(ResourceKey<? extends Registry<T>> key)
		{
			this.key = key;
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
			DeferredRegister<T> register = DeferredRegister.create(this.key, modid);
			register.register(FMLJavaModLoadingContext.get().getModEventBus());
			return register;
		}

		@Override
		public ResourceLocation getId(T value)
		{
			return RegistryManager.ACTIVE.getRegistry(this.key).getKey(value);
		}

		@Override
		public T getValue(ResourceLocation id)
		{
			return RegistryManager.ACTIVE.getRegistry(this.key).getValue(id);
		}

	}

}