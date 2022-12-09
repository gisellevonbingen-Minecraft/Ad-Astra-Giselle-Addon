package ad_astra_giselle_addon.common.registry;

import net.minecraft.resources.ResourceLocation;

public class DelegateDoubleHolder<P, S>
{
	private final ResourceLocation id;
	private final ObjectRegistryHolder<? extends P> primary;
	private final ObjectRegistryHolder<? extends S> secondary;

	public DelegateDoubleHolder(ObjectRegistryHolder<? extends P> primary, ObjectRegistryHolder<? extends S> secondary)
	{
		this.id = primary.getId();
		this.primary = primary;
		this.secondary = secondary;
	}

	public ResourceLocation getId()
	{
		return this.id;
	}

	public P getPrimary()
	{
		return this.primary.get();
	}

	public S getSecondary()
	{
		return this.secondary.get();
	}

}
