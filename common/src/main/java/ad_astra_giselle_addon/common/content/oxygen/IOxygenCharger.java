package ad_astra_giselle_addon.common.content.oxygen;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;

public interface IOxygenCharger
{
	public default List<IChargeMode> getAvailableChargeModes()
	{
		return Arrays.asList(ChargeMode.values());
	}

	@NotNull
	public IChargeMode getChargeMode();

	public void setChargeMode(@Nullable IChargeMode mode);

	public long getTransferAmount();

	public UniveralFluidHandler getFluidHandler();

	public Range<Integer> getTemperatureThreshold();

	public default long getTotalAmount()
	{
		UniveralFluidHandler fluidHandler = this.getFluidHandler();
		int size = fluidHandler.getTankAmount();
		long amount = 0L;

		for (int i = 0; i < size; i++)
		{
			amount += fluidHandler.getFluidInTank(i).getFluidAmount();
		}

		return amount;
	}

	public default long getTotalCapacity()
	{
		UniveralFluidHandler fluidHandler = this.getFluidHandler();
		int size = fluidHandler.getTankAmount();
		long capacity = 0L;

		for (int i = 0; i < size; i++)
		{
			capacity += fluidHandler.getTankCapacity(i);
		}

		return capacity;
	}

	public default double getStoredRatio()
	{
		long amount = this.getTotalAmount();
		long capacity = this.getTotalCapacity();
		return FluidHooks2.getStoredRatio(amount, capacity);
	}

}
