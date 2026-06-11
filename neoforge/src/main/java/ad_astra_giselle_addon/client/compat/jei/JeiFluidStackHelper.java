package ad_astra_giselle_addon.client.compat.jei;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class JeiFluidStackHelper implements IJeiFluidStackHelper<FluidStack>
{
	@SuppressWarnings("deprecation")
	@Override
	public FluidStack get(Fluid fluid, long amount, @Nullable DataComponentPatch dataComponentPatch)
	{
		return new FluidStack(fluid.builtInRegistryHolder(), (int) amount, dataComponentPatch);
	}

}
