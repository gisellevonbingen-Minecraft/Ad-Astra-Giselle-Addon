package ad_astra_giselle_addon.common.registries;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class DeferredRegisterWrapper<T>
{
	private final String modid;
	private final DeferredRegister<T> impl;
	private final Set<T> objects;
	private final Set<T> readonlyObjects;

	protected DeferredRegisterWrapper(String modid, DeferredRegister<T> impl)
	{
		this.modid = modid;
		this.impl = impl;
		this.objects = new HashSet<>();
		this.readonlyObjects = Collections.unmodifiableSet(this.objects);
	}

	protected DeferredRegisterWrapper(String modid, IForgeRegistry<T> reg)
	{
		this(modid, DeferredRegister.create(reg, modid));
	}

	protected DeferredRegisterWrapper(String modid, ResourceKey<? extends Registry<T>> reg)
	{
		this(modid, DeferredRegister.create(reg, modid));
	}

	public static <T> DeferredRegisterWrapper<T> create(String modid, IForgeRegistry<T> reg)
	{
		return new DeferredRegisterWrapper<>(modid, reg);
	}

	public static <T> DeferredRegisterWrapper<T> create(String modid, ResourceKey<? extends Registry<T>> reg)
	{
		return new DeferredRegisterWrapper<>(modid, reg);
	}

	public void register(IEventBus bus)
	{
		this.impl.register(bus);
	}

	public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> sup)
	{
		return this.impl.register(name, () ->
		{
			I object = sup.get();
			DeferredRegisterWrapper.this.objects.add(object);
			return object;
		});
	}

	public String getModid()
	{
		return this.modid;
	}

	public Collection<RegistryObject<T>> getEntries()
	{
		return this.impl.getEntries();
	}

	public Collection<T> getObjects()
	{
		return this.readonlyObjects;
	}

}
