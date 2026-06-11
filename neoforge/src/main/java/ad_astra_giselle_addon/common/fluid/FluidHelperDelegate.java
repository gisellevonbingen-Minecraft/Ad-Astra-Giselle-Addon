package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidHelperDelegate implements FluidHelper.Delegate
{
	@Override
	public Component getDisplayName(ResourceStack<FluidResource> fluid)
	{
		return new FluidStack(fluid.resource().asHolder(), (int) fluid.amount(), fluid.resource().getDataPatch()).getHoverName();
	}

}
