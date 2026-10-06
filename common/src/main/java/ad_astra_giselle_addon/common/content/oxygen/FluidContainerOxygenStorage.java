package ad_astra_giselle_addon.common.content.oxygen;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import net.minecraft.world.entity.LivingEntity;

public class FluidContainerOxygenStorage implements IOxygenStorage
{
	private final CommonStorage<FluidResource> fluidContainer;
	private final boolean canUseOnCold;
	private final boolean canUseOnHot;

	public FluidContainerOxygenStorage(CommonStorage<FluidResource> fluidContainer, boolean canUseOnCold, boolean canUseOnHot)
	{
		this.fluidContainer = fluidContainer;
		this.canUseOnCold = canUseOnCold;
		this.canUseOnHot = canUseOnHot;
	}

	@Override
	public boolean canUseOnCold()
	{
		return this.canUseOnCold;
	}

	@Override
	public boolean canUseOnHot()
	{
		return this.canUseOnHot;
	}

	@Override
	public long extractOxygen(@Nullable LivingEntity entity, long amount, boolean simulate)
	{
		return FluidUtils2.extractFluid(this.fluidContainer, FluidPredicates::isOxygen, amount, simulate).amount();
	}

	@Override
	public long getOxygenAmount()
	{
		return FluidUtils2.getAmount(this.fluidContainer, FluidPredicates::isOxygen);
	}

	@Override
	public long getOxygenCapacity()
	{
		return FluidUtils2.getCapacity(this.fluidContainer);
	}

}
