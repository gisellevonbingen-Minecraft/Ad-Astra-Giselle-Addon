package ad_astra_giselle_addon.client.compat.jei;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import mezz.jei.fabric.ingredients.fluid.JeiFluidIngredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

public class FluidStackHelper
{
	public static JeiFluidIngredient get(FluidHolder fluid)
	{
		return new JeiFluidIngredient(fluid.getFluid(), fluid.getFluidAmount(), fluid.getCompound());
	}

	public static JeiFluidIngredient get(Fluid fluid, long amount)
	{
		return new JeiFluidIngredient(fluid, amount);
	}

	public static JeiFluidIngredient get(Fluid fluid, long amount, @Nullable CompoundTag tag)
	{
		return new JeiFluidIngredient(fluid, amount, tag);
	}

}
