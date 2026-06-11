package ad_astra_giselle_addon.common.content.oxygen;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.base.StorageSlot;
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

	CommonStorage<FluidResource> getFluidContainer();

	@Override
	default long extractOxygen(@Nullable LivingEntity entity, long amount, boolean simulate)
	{
		CommonStorage<FluidResource> fluidContainer = this.getFluidContainer();
		return FluidUtils2.extractFluid(fluidContainer, FluidPredicates::isOxygen, amount, simulate).amount();
	}

	@Override
	default long getOxygenAmount()
	{
		CommonStorage<FluidResource> fluidContainer = this.getFluidContainer();
		long sum = 0L;

		for (int i = 0; i < fluidContainer.size(); i++)
		{
			ResourceStack<FluidResource> fluid = fluidContainer.getContents(i);

			if (FluidPredicates.isOxygen(fluid))
			{
				sum += fluid.amount();
			}

		}

		return sum;
	}

	@Override
	default long getOxygenCapacity()
	{
		CommonStorage<FluidResource> fluidContainer = this.getFluidContainer();
		long capacity = 0L;

		for (int i = 0; i < fluidContainer.size(); i++)
		{
			StorageSlot<FluidResource> slot = fluidContainer.get(i);
			capacity += slot.getLimit(slot.getResource());
		}

		return capacity;
	}

}
