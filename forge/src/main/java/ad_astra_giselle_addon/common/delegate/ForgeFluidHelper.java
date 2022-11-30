package ad_astra_giselle_addon.common.delegate;

import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import earth.terrarium.botarium.api.fluid.PlatformFluidItemHandler;
import earth.terrarium.botarium.forge.fluid.ForgeFluidHandler;
import earth.terrarium.botarium.forge.fluid.ForgeFluidItemHandler;

public class ForgeFluidHelper implements DelegateFluidHelper
{
	public static final ForgeFluidHelper INTANCE = new ForgeFluidHelper();

	@Override
	public int getTankCount(PlatformFluidHandler handler)
	{
		if (handler instanceof ForgeFluidHandler internal)
		{
			return internal.handler().getTanks();
		}
		else
		{
			return 0;
		}

	}

	@Override
	public long getCapacity(PlatformFluidHandler handler, int tank)
	{
		if (handler instanceof ForgeFluidHandler internal)
		{
			return internal.handler().getTankCapacity(tank);
		}
		else
		{
			return 0;
		}

	}

	@Override
	public int getTankCount(PlatformFluidItemHandler handler)
	{
		if (handler instanceof ForgeFluidItemHandler internal)
		{
			return internal.handler().getTanks();
		}
		else
		{
			return 0;
		}

	}

	@Override
	public long getCapacity(PlatformFluidItemHandler handler, int tank)
	{
		if (handler instanceof ForgeFluidItemHandler internal)
		{
			return internal.handler().getTankCapacity(tank);
		}
		else
		{
			return 0;
		}

	}

	private ForgeFluidHelper()
	{

	}

}
