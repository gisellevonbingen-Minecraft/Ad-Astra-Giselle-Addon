package ad_astra_giselle_addon.common.delegate;

import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import earth.terrarium.botarium.api.fluid.PlatformFluidItemHandler;

public interface DelegateFluidHelper
{
	int getTankCount(PlatformFluidHandler handler);

	long getCapacity(PlatformFluidHandler handler, int tank);

	int getTankCount(PlatformFluidItemHandler handler);

	long getCapacity(PlatformFluidItemHandler handler, int tank);
}
