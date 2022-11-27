package ad_astra_giselle_addon.common.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class DoubleRegistryObject<P, S>
{
	private final ResourceLocation id;
	private final RegistryObject<? extends P> primary;
	private final RegistryObject<? extends S> secondary;

	public DoubleRegistryObject(RegistryObject<? extends P> primary, RegistryObject<? extends S> secondary)
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
