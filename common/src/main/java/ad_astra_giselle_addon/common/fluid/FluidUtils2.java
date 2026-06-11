package ad_astra_giselle_addon.common.fluid;

import java.util.Collection;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.base.UpdateManager;

public class FluidUtils2
{
	public static boolean notEmptyAndTest(ResourceStack<FluidResource> fluid, @Nullable Predicate<FluidResource> predicate)
	{
		if (fluid.isEmpty())
		{
			return false;
		}
		else if (predicate != null && !predicate.test(fluid.resource()))
		{
			return false;
		}
		else
		{
			return true;
		}

	}

	public static double getStoredRatio(long amount, long capacity)
	{
		return capacity == 0L ? 0.0D : (double) amount / capacity;
	}

	public static ResourceStack<FluidResource> extractFluid(CommonStorage<FluidResource> fluidContainer, @Nullable Predicate<FluidResource> predicate, long amount, boolean simulate)
	{
		for (int i = 0; i < fluidContainer.size(); i++)
		{
			ResourceStack<FluidResource> fluid = fluidContainer.getContents(i);

			if (!notEmptyAndTest(fluid, predicate))
			{
				continue;
			}

			long extracting = fluidContainer.extract(fluid.resource(), amount, simulate);
	        UpdateManager.batch(fluidContainer);

			if (extracting > 0L)
			{
				return fluid.withCount(extracting);
			}

		}

		return ResourceStack.EMPTY_FLUID;
	}

	public static ResourceStack<FluidResource> extractFluid(CommonStorage<FluidResource> fluidContainer, @Nullable Predicate<FluidResource> predicate, boolean simulate)
	{
		for (int i = 0; i < fluidContainer.size(); i++)
		{
			ResourceStack<FluidResource> fluid = fluidContainer.getContents(i);

			if (!notEmptyAndTest(fluid, predicate))
			{
				continue;
			}

			long extracting = fluidContainer.extract(fluid.resource(), fluid.amount(), simulate);
	        UpdateManager.batch(fluidContainer);

			if (extracting > 0L)
			{
				return fluid.withCount(extracting);
			}

		}

		return ResourceStack.EMPTY_FLUID;
	}

	public static ResourceStack<FluidResource> insertFluidAny(CommonStorage<FluidResource> fluidHandler, Collection<FluidResource> fluids, boolean simulate)
	{
		for (int i = 0; i < fluidHandler.size(); i++)
		{
			ResourceStack<FluidResource> fluid = fluidHandler.getContents(i);

			if (fluid.isEmpty())
			{
				continue;
			}

			long insertAmount = fluidHandler.insert(fluid.resource(), fluid.amount(), simulate);
	        UpdateManager.batch(fluidHandler);

			if (insertAmount > 0L)
			{
				return fluid.withCount(insertAmount);
			}

		}

		return ResourceStack.EMPTY_FLUID;
	}

	public static ResourceStack<FluidResource> moveFluidAny(CommonStorage<FluidResource> from, CommonStorage<FluidResource> to, @Nullable Predicate<FluidResource> predicate, long amount, boolean simulate)
	{
		ResourceStack<FluidResource> extracting = extractFluid(from, predicate, amount, true);

		if (extracting.isEmpty())
		{
			return ResourceStack.EMPTY_FLUID;
		}

		ResourceStack<FluidResource> inserting = extracting.withCount(to.insert(extracting.resource(), extracting.amount(), true));

		if (!simulate && !inserting.isEmpty())
		{
			long inserted = to.insert(inserting.resource(), inserting.amount(), false);
			from.extract(inserting.resource(), inserted, false);
	        UpdateManager.batch(from, to);
		}

		return inserting;
	}

	public static ResourceStack<FluidResource> moveFluidAny(CommonStorage<FluidResource> from, CommonStorage<FluidResource> to, @Nullable Predicate<FluidResource> predicate, boolean simulate)
	{
		ResourceStack<FluidResource> extracting = extractFluid(from, predicate, true);

		if (extracting.isEmpty())
		{
			return ResourceStack.EMPTY_FLUID;
		}

		ResourceStack<FluidResource> inserting = extracting.withCount(to.insert(extracting.resource(), extracting.amount(), true));

		if (!simulate && !inserting.isEmpty())
		{
			long inserted = to.insert(inserting.resource(), inserting.amount(), false);
			from.extract(inserting.resource(), inserted, false);
	        UpdateManager.batch(from, to);
		}

		return inserting;
	}

	private FluidUtils2()
	{

	}

}
