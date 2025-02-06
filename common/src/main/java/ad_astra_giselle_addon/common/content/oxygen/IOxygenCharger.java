package ad_astra_giselle_addon.common.content.oxygen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.world.entity.LivingEntity;

public interface IOxygenCharger extends IOxygenStorage
{
	public default List<IChargeMode> getAvailableChargeModes()
	{
		return Arrays.asList(ChargeMode.values());
	}

	@NotNull
	IChargeMode getChargeMode();

	void setChargeMode(@Nullable IChargeMode mode);

	long getTransferAmount();

	FluidContainer getFluidContainer();

	@Override
	default long extractOxygen(@Nullable LivingEntity entity, long amount, boolean simulate)
	{
		FluidContainer fluidContainer = this.getFluidContainer();
		return FluidUtils2.extractFluid(fluidContainer, FluidPredicates::isOxygen, amount, simulate).getFluidAmount();
	}

	@Override
	default long getOxygenAmount()
	{
		return this.getFluidContainer().getFluids().stream().filter(FluidPredicates::isOxygen).collect(Collectors.summingLong(FluidHolder::getFluidAmount));
	}

	@Override
	default long getOxygenCapacity()
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

}
