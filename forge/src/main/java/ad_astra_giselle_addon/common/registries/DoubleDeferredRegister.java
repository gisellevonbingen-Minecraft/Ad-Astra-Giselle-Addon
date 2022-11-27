package ad_astra_giselle_addon.common.registries;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class DoubleDeferredRegister<P, S>
{
	private final String modid;
	protected final DeferredRegisterWrapper<P> primaryRegister;
	protected final DeferredRegisterWrapper<S> secondaryRegister;

	public DoubleDeferredRegister(String modid, IForgeRegistry<P> primaryRegistry, IForgeRegistry<S> secondaryRegistry)
	{
		this.modid = modid;
		this.primaryRegister = DeferredRegisterWrapper.create(modid, primaryRegistry);
		this.secondaryRegister = DeferredRegisterWrapper.create(modid, secondaryRegistry);
	}

	public void register(IEventBus bus)
	{
		this.primaryRegister.register(bus);
		this.secondaryRegister.register(bus);
	}

	protected <P2 extends P, S2 extends S, R extends DoubleRegistryObject<P2, S2>> R register(String name, Supplier<? extends P2> primarySupplier, Function<P2, ? extends S2> secondaryFunction, BiFunction<RegistryObject<P2>, RegistryObject<S2>, R> registryFuction)
	{
		RegistryObject<P2> primary = this.primaryRegister.register(name, primarySupplier);
		RegistryObject<S2> secondary = this.secondaryRegister.register(name, () -> secondaryFunction.apply(primary.get()));
		return registryFuction.apply(primary, secondary);
	}

	public String getModid()
	{
		return this.modid;
	}

	public Collection<RegistryObject<P>> getPrimaryEntries()
	{
		return this.primaryRegister.getEntries();
	}

	public Collection<P> getPrimaryObjects()
	{
		return this.primaryRegister.getObjects();
	}

	public Collection<RegistryObject<S>> getSecondaryEntries()
	{
		return this.secondaryRegister.getEntries();
	}

	public Collection<S> getSecondaryObjects()
	{
		return this.secondaryRegister.getObjects();
	}

}
