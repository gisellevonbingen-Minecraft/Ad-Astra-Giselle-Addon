package ad_astra_giselle_addon.client.compat.jei;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.material.Fluid;

public interface IJeiFluidStackHelper<T>
{
	public static final JeiFluidStackHelper INSTANCE = new JeiFluidStackHelper();

	public default T get(ResourceStack<FluidResource> fluid)
	{
		return this.get(fluid.resource().getType(), fluid.amount(), fluid.resource().getDataPatch());
	}

	public default T get(Fluid fluid, long amount)
	{
		return this.get(fluid, (int) amount, null);
	}

	T get(Fluid fluid, long amount, @Nullable DataComponentPatch dataComponentPatch);
}
