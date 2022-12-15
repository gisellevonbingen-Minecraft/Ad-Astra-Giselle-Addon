package ad_astra_giselle_addon.common.registry;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class DoubleRegistryCollection<P, S>
{
	private final String modid;
	protected final ObjectRegistryCollection<P> primaryRegister;
	protected final ObjectRegistryCollection<S> secondaryRegister;

	public DoubleRegistryCollection(String modid, ResourceKey<? extends Registry<P>> primaryRegistry, ResourceKey<? extends Registry<S>> secondaryRegistry)
	{
		this.modid = modid;
		this.primaryRegister = new ObjectRegistryCollection<>(modid, primaryRegistry);
		this.secondaryRegister = new ObjectRegistryCollection<>(modid, secondaryRegistry);
	}

	public void register()
	{
		this.primaryRegister.register();
		this.secondaryRegister.register();
	}

	protected <P2 extends P, S2 extends S, R extends DoubleRegistryHolder<P2, S2>> R add(String name, Supplier<P2> primarySupplier, Function<P2, S2> secondaryFunction, BiFunction<ObjectRegistryHolder<P2>, ObjectRegistryHolder<S2>, R> registryFuction)
	{
		ObjectRegistryHolder<P2> primary = this.primaryRegister.add(name, primarySupplier);
		ObjectRegistryHolder<S2> secondary = this.secondaryRegister.add(name, () -> secondaryFunction.apply(primary.get()));
		return registryFuction.apply(primary, secondary);
	}

	public String getModid()
	{
		return this.modid;
	}

	public Collection<ObjectRegistryHolder<P>> getPrimaryObjects()
	{
		return this.primaryRegister.getObjects();
	}

	public Collection<P> getPrimaryValues()
	{
		return this.primaryRegister.getValues();
	}

	public Collection<ObjectRegistryHolder<S>> getSecondaryObjects()
	{
		return this.secondaryRegister.getObjects();
	}

	public Collection<S> getSecondaryValues()
	{
		return this.secondaryRegister.getValues();
	}

}
