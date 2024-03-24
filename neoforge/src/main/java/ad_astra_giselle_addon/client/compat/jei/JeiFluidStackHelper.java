package ad_astra_giselle_addon.client.compat.jei;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class JeiFluidStackHelper implements IJeiFluidStackHelper<FluidStack>
{
	@Override
	public FluidStack get(Fluid fluid, long amount, @Nullable CompoundTag tag)
	{
		return new FluidStack(fluid, (int) amount, tag);
	}

}
