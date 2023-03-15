package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.network.chat.Component;

public class FluidHelper
{
	public static final FluidHelper.Delegate DELEGATE = new FluidHelperDelegate();

	public static Component getDisplayName(FluidHolder fluid)
	{
		return DELEGATE.getDisplayName(fluid);
	}

	public static interface Delegate
	{
		Component getDisplayName(FluidHolder fluid);
	}

}
