package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.botarium.api.fluid.FluidHolder;
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
		return hasTag(fluid.getFluid(), tag);
	}

	public static boolean isOxygen(Fluid fluid)
	{
		return hasTag(fluid, ModTags.OXYGEN);
	}

	public static boolean isOxygen(FluidHolder fluid)
	{
		return isOxygen(fluid.getFluid());
	}

	public static boolean isOxygen(int tank, FluidHolder fluid)
	{
		return isOxygen(fluid);
	}

	public static boolean isFuel(Fluid fluid)
	{
		return hasTag(fluid, ModTags.FUELS);
	}

	public static boolean isFuel(FluidHolder fluid)
	{
		return isFuel(fluid.getFluid());
	}

	public static boolean isFuel(int tank, FluidHolder fluid)
	{
		return isFuel(fluid);
	}

	private FluidPredicates()
	{

	}

}
