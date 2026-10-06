package ad_astra_giselle_addon.common.content.oxygen;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import net.minecraft.world.entity.LivingEntity;

public class FluidContainerOxygenStorage implements IOxygenStorage
{
	private final FluidContainer fluidContainer;
	private final boolean canUseOnCold;
	private final boolean canUseOnHot;

	public FluidContainerOxygenStorage(FluidContainer fluidContainer, boolean canUseOnCold, boolean canUseOnHot)
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
		return FluidUtils2.extractFluid(this.fluidContainer, FluidPredicates::isOxygen, amount, simulate).getFluidAmount();
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
