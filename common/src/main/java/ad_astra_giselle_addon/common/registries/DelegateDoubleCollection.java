package ad_astra_giselle_addon.common.registries;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import ad_astra_giselle_addon.common.delegate.DelegateRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class DelegateDoubleCollection<P, S>
{
	private final String modid;
	protected final DelegateObjectCollection<P> primaryRegister;
	protected final DelegateObjectCollection<S> secondaryRegister;

	public DelegateDoubleCollection(String modid, ResourceKey<? extends Registry<P>> primaryRegistry, ResourceKey<? extends Registry<S>> secondaryRegistry)
	{
		this.modid = modid;
		this.primaryRegister = new DelegateObjectCollection<>(modid, primaryRegistry);
		this.secondaryRegister = new DelegateObjectCollection<>(modid, secondaryRegistry);
	}

	public void register(DelegateRegistryHelper factory)
	{
		this.primaryRegister.register(factory);
		this.secondaryRegister.register(factory);
	}

	protected <P2 extends P, S2 extends S, R extends DelegateDoubleHolder<P2, S2>> R register(String name, Supplier<? extends P2> primarySupplier, Function<P2, ? extends S2> secondaryFunction, BiFunction<DelegateObjectHolder<P2>, DelegateObjectHolder<S2>, R> registryFuction)
	{
		DelegateObjectHolder<P2> primary = this.primaryRegister.register(name, primarySupplier);
		DelegateObjectHolder<S2> secondary = this.secondaryRegister.register(name, () -> secondaryFunction.apply(primary.get()));
		return registryFuction.apply(primary, secondary);
	}

	public String getModid()
	{
		return this.modid;
	}

	public Collection<DelegateObjectHolder<P>> getPrimaryObjects()
	{
		return this.primaryRegister.getObjects();
	}

	public Collection<DelegateObjectHolder<S>> getSecondaryObjects()
	{
		return this.secondaryRegister.getObjects();
	}

}
