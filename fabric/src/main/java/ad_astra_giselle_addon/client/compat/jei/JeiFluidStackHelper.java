package ad_astra_giselle_addon.client.compat.jei;

import org.jetbrains.annotations.Nullable;

import mezz.jei.fabric.ingredients.fluid.JeiFluidIngredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

public class JeiFluidStackHelper implements IJeiFluidStackHelper<JeiFluidIngredient>
{
	@Override
	public JeiFluidIngredient get(Fluid fluid, long amount, @Nullable CompoundTag tag)
	{
		return new JeiFluidIngredient(fluid, amount, tag);
	}

}
