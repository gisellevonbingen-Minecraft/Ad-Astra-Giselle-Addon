package ad_astra_giselle_addon.common.delegate;

import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import earth.terrarium.botarium.api.fluid.PlatformFluidItemHandler;
import earth.terrarium.botarium.fabric.fluid.FabricFluidHandler;
import earth.terrarium.botarium.fabric.fluid.FabricFluidItemHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;

public class FabricFluidHelper implements DelegateFluidHelper
{
	public static final FabricFluidHelper INTANCE = new FabricFluidHelper();

	public StorageView<FluidVariant> getView(Storage<FluidVariant> storage, int index)
	{
		int count = 0;

		for (StorageView<FluidVariant> view : storage)
		{
			if (count == index)
			{
				return view;
			}

			count++;
		}

		return null;
	}

	public int getViewCount(Storage<FluidVariant> storage)
	{
		int size = 0;

		for (@SuppressWarnings("unused") StorageView<FluidVariant> view : storage)
		{
			size++;
		}

		return size;
	}

	@Override
	public int getTankCount(PlatformFluidHandler handler)
	{
		if (handler instanceof FabricFluidHandler internal)
		{
			return this.getViewCount(internal.storage());
		}
		else
		{
			return 0;
		}

	}

	@Override
	public long getCapacity(PlatformFluidHandler handler, int tank)
	{
		if (handler instanceof FabricFluidHandler internal)
		{
			return this.getView(internal.storage(), tank).getCapacity();
		}
		else
		{
			return 0;
		}

	}

	@Override
	public int getTankCount(PlatformFluidItemHandler handler)
	{
		if (handler instanceof FabricFluidItemHandler internal)
		{
			return this.getViewCount(internal.storage());
		}
		else
		{
			return 0;
		}

	}

	@Override
	public long getCapacity(PlatformFluidItemHandler handler, int tank)
	{
		if (handler instanceof FabricFluidItemHandler internal)
		{
			return this.getView(internal.storage(), tank).getCapacity();
		}
		else
		{
			return 0;
		}

	}

	private FabricFluidHelper()
	{

	}

}
