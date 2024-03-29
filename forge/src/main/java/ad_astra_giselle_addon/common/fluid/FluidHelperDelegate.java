package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

public class FluidHelperDelegate implements FluidHelper.Delegate
{
	@Override
	public Component getDisplayName(FluidHolder fluid)
	{
		return new FluidStack(fluid.getFluid(), (int) fluid.getFluidAmount(), fluid.getCompound()).getDisplayName();
	}

}
