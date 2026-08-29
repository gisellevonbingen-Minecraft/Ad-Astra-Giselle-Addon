package ad_astra_giselle_addon.client.compat.jei;

import org.jetbrains.annotations.Nullable;

import mezz.jei.fabric.ingredients.fluid.JeiFluidIngredient;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.material.Fluid;

public class JeiFluidStackHelper implements IJeiFluidStackHelper<JeiFluidIngredient>
{
	@Override
	public JeiFluidIngredient get(Fluid fluid, long amount, @Nullable DataComponentPatch dataComponentPatch)
	{
		return new JeiFluidIngredient(FluidVariant.of(fluid, dataComponentPatch), amount);
	}

}
