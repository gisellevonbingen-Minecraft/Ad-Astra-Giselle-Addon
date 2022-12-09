package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

public class FluidHelper implements IFluidHelper
{
	@Override
	public Component getDisplayName(FluidHolder fluid)
	{
		return new FluidStack(fluid.getFluid(), (int) fluid.getFluidAmount(), fluid.getCompound()).getDisplayName();
	}

}
