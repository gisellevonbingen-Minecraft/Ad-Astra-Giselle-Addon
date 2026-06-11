package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class FluidPredicates
{
	@SuppressWarnings("deprecation")
	public static boolean hasTag(Fluid fluid, TagKey<Fluid> tag)
	{
		return fluid.is(tag);
	}

	public static boolean hasTag(FluidResource fluid, TagKey<Fluid> tag)
	{
		return fluid.is(tag);
	}

	public static boolean isOxygen(FluidResource fluid)
	{
		return hasTag(fluid, ModFluidTags.OXYGEN);
	}

	public static boolean isOxygen(ResourceStack<FluidResource> fluid)
	{
		return isOxygen(fluid.resource());
	}

	public static boolean isOxygen(int tank, FluidResource fluid)
	{
		return isOxygen(fluid);
	}

	private FluidPredicates()
	{

	}

}
