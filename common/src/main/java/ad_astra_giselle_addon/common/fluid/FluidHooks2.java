package ad_astra_giselle_addon.common.fluid;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;

public class FluidHooks2
{
	public static final long MB_1 = FluidHooks.buckets(1) / 1000;
	
	public static FluidHolder deriveAmount(FluidHolder fluid, long newAmount)
	{
		FluidHolder derive = fluid.copyHolder();
		derive.setAmount(newAmount);
		return derive;
	}

	public static boolean notEmptyAndTest(FluidHolder fluid, @Nullable Predicate<FluidHolder> predicate)
	{
		if (fluid.isEmpty())
		{
			return false;
		}
		else if (predicate != null && !predicate.test(fluid))
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

	public static double getStoredRatio(UniveralFluidHandler fluidHandler)
	{
		long amount = getStoredAmount(fluidHandler);
		long capacity = getTotalCapacity(fluidHandler);
		return getStoredRatio(amount, capacity);
	}

	public static long getStoredAmount(UniveralFluidHandler fluidHandler)
	{
		long amount = 0L;

		for (FluidHolder fluid : fluidHandler.getFluidTanks())
		{
			if (fluid.isEmpty())
			{
				continue;
			}

			amount += fluid.getFluidAmount();
		}

		return amount;
	}

	public static long getTotalCapacity(UniveralFluidHandler fluidHandler)
	{
		int size = fluidHandler.getTankAmount();
		long capacity = 0L;

		for (int i = 0; i < size; i++)
		{
			capacity += fluidHandler.getTankCapacity(i);
		}

		return capacity;
	}

	public static FluidHolder extractFluid(UniveralFluidHandler fluidHandler, @Nullable Predicate<FluidHolder> predicate, long amount, boolean simulate)
	{
		int tanks = fluidHandler.getTankAmount();

		for (int i = 0; i < tanks; i++)
		{
			FluidHolder fluid = fluidHandler.getFluidInTank(i);

			if (!notEmptyAndTest(fluid, predicate))
			{
				continue;
			}

			FluidHolder extracting = fluidHandler.extractFluid(deriveAmount(fluid, amount), simulate);

			if (!extracting.isEmpty())
			{
				return extracting;
			}

		}

		return FluidHooks.emptyFluid();
	}

	public static FluidHolder insertFluidAny(UniveralFluidHandler fluidHandler, Collection<FluidHolder> fluids, boolean simulate)
	{
		for (FluidHolder fluid : fluids)
		{
			if (fluid.isEmpty())
			{
				continue;
			}

			long insertAmount = fluidHandler.insertFluid(fluid, simulate);

			if (insertAmount > 0L)
			{
				return deriveAmount(fluid, insertAmount);
			}

		}

		return FluidHooks.emptyFluid();
	}

	public static FluidHolder moveFluidAny(UniveralFluidHandler from, UniveralFluidHandler to, @Nullable Predicate<FluidHolder> predicate, long amount, boolean simulate)
	{
		List<FluidHolder> fluids = from.getFluidTanks();

		for (FluidHolder fluid : fluids)
		{
			if (!notEmptyAndTest(fluid, predicate))
			{
				continue;
			}

			FluidHolder extracting = extractFluid(from, predicate, amount, true);
			long insertingAmount = to.insertFluid(extracting, true);

			if (insertingAmount > 0L)
			{
				FluidHolder inserting = deriveAmount(fluid, insertingAmount);

				if (!simulate)
				{
					extractFluid(from, predicate, insertingAmount, false);
					to.insertFluid(inserting, false);
				}

				return inserting;
			}

		}

		return FluidHooks.emptyFluid();

	}

	private FluidHooks2()
	{

	}

}
