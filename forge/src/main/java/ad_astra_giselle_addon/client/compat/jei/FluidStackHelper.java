package ad_astra_giselle_addon.client.compat.jei;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackHelper
{
	public static FluidStack get(FluidHolder fluid)
	{
		return new FluidStack(fluid.getFluid(), (int) fluid.getFluidAmount(), fluid.getCompound());
	}

	public static FluidStack get(Fluid fluid, long amount)
	{
		return new FluidStack(fluid, (int) amount);
	}

	public static FluidStack get(Fluid fluid, long amount, @Nullable CompoundTag tag)
	{
		return new FluidStack(fluid, (int) amount, tag);
	}

}
