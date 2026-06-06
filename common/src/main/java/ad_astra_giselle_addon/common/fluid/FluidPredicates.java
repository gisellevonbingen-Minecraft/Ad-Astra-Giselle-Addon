package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class FluidPredicates
{
	@SuppressWarnings("deprecation")
	public static boolean hasTag(Fluid fluid, TagKey<Fluid> tag)
	{
		return fluid.is(tag);
	}

	public static boolean hasTag(FluidHolder fluid, TagKey<Fluid> tag)
	{
		return fluid.is(tag);
	}

	public static boolean isOxygen(FluidHolder fluid)
	{
		return hasTag(fluid, ModFluidTags.OXYGEN);
	}

	public static boolean isOxygen(int tank, FluidHolder fluid)
	{
		return isOxygen(fluid);
	}

	private FluidPredicates()
	{

	}

}
