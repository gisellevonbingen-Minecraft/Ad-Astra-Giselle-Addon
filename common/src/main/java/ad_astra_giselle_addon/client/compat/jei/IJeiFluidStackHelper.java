package ad_astra_giselle_addon.client.compat.jei;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

public interface IJeiFluidStackHelper<T>
{
	public static final JeiFluidStackHelper INSTANCE = new JeiFluidStackHelper();

	public default T get(FluidHolder fluid)
	{
		return this.get(fluid.getFluid(), fluid.getFluidAmount(), fluid.getCompound());
	}

	public default T get(Fluid fluid, long amount)
	{
		return this.get(fluid, (int) amount, null);
	}

	T get(Fluid fluid, long amount, @Nullable CompoundTag tag);
}
