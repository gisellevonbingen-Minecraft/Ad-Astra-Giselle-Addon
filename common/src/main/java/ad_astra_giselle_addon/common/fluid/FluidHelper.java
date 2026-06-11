package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import net.minecraft.network.chat.Component;

public class FluidHelper
{
	public static final FluidHelper.Delegate DELEGATE = new FluidHelperDelegate();

	public static Component getDisplayName(ResourceStack<FluidResource> fluid)
	{
		return DELEGATE.getDisplayName(fluid);
	}

	public static interface Delegate
	{
		Component getDisplayName(ResourceStack<FluidResource> fluid);
	}

}
