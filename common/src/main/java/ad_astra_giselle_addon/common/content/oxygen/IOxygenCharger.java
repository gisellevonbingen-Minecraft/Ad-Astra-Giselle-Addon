package ad_astra_giselle_addon.common.content.oxygen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;

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

	public FluidContainer getFluidContainer();

	public boolean canUseOnCold();

	public boolean canUseOnHot();

	public default boolean canUse(boolean isCold, boolean isHot)
	{
		if (isCold && !this.canUseOnCold())
		{
			return false;
		}
		else if (isHot && !this.canUseOnHot())
		{
			return false;
		}
		else
		{
			return true;
		}

	}

	public default long getTotalAmount()
	{
		return this.getFluidContainer().getFluids().stream().collect(Collectors.summingLong(FluidHolder::getFluidAmount));
	}

	public default long getTotalCapacity()
	{
		FluidContainer fluidContainer = this.getFluidContainer();
		int size = fluidContainer.getSize();
		long capacity = 0L;

		for (int i = 0; i < size; i++)
		{
			capacity += fluidContainer.getTankCapacity(i);
		}

		return capacity;
	}

	public default double getStoredRatio()
	{
		long amount = this.getTotalAmount();
		long capacity = this.getTotalCapacity();
		return FluidUtils2.getStoredRatio(amount, capacity);
	}

}
