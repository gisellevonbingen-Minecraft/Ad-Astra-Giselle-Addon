package ad_astra_giselle_addon.common.fluid;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;

public class FluidUtils2
{
	public static final long BUCKET = FluidConstants.getBucketAmount();
	public static final long MILLI_BUCKET = FluidConstants.fromMillibuckets(1);

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

	public static FluidHolder extractFluid(FluidContainer fluidContainer, @Nullable Predicate<FluidHolder> predicate, long amount, boolean simulate)
	{
		List<FluidHolder> fluids = fluidContainer.getFluids();

		for (FluidHolder fluid : fluids)
		{
			if (!notEmptyAndTest(fluid, predicate))
			{
				continue;
			}

			FluidHolder extracting = fluidContainer.extractFluid(fluid.copyWithAmount(amount), simulate);

			if (!extracting.isEmpty())
			{
				return extracting;
			}

		}

		return FluidHolder.empty();
	}

	public static FluidHolder extractFluid(FluidContainer fluidContainer, @Nullable Predicate<FluidHolder> predicate, boolean simulate)
	{
		List<FluidHolder> fluids = fluidContainer.getFluids();

		for (FluidHolder fluid : fluids)
		{
			if (!notEmptyAndTest(fluid, predicate))
			{
				continue;
			}

			FluidHolder extracting = fluidContainer.extractFluid(fluid, simulate);

			if (!extracting.isEmpty())
			{
				return extracting;
			}

		}

		return FluidHolder.empty();
	}

	public static FluidHolder insertFluidAny(FluidContainer fluidHandler, Collection<FluidHolder> fluids, boolean simulate)
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
				return fluid.copyWithAmount(insertAmount);
			}

		}

		return FluidHolder.empty();
	}

	public static FluidHolder moveFluidAny(FluidContainer from, FluidContainer to, @Nullable Predicate<FluidHolder> predicate, long amount, boolean simulate)
	{
		FluidHolder extracting = extractFluid(from, predicate, amount, true);

		if (extracting.isEmpty())
		{
			return FluidHolder.empty();
		}

		FluidHolder inserting = extracting.copyWithAmount(to.insertFluid(extracting, true));

		if (!simulate && !inserting.isEmpty())
		{
			from.extractFluid(inserting, false);
			to.insertFluid(inserting, false);
		}

		return inserting;
	}

	public static FluidHolder moveFluidAny(FluidContainer from, FluidContainer to, @Nullable Predicate<FluidHolder> predicate, boolean simulate)
	{
		FluidHolder extracting = extractFluid(from, predicate, true);

		if (extracting.isEmpty())
		{
			return FluidHolder.empty();
		}

		FluidHolder inserting = extracting.copyWithAmount(to.insertFluid(extracting, true));

		if (!simulate && !inserting.isEmpty())
		{
			from.extractFluid(inserting, false);
			to.insertFluid(inserting, false);
		}

		return inserting;

	}

	private FluidUtils2()
	{

	}

}
