package ad_astra_giselle_addon.common.delegate;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import net.minecraft.network.chat.Component;

public interface DelegateFluidHelper
{
	Component getDisplayName(FluidHolder fluid);
}
