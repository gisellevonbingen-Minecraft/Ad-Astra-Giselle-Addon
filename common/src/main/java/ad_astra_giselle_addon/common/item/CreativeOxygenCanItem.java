package ad_astra_giselle_addon.common.item;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.fluid.CreativeFluidContainer;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.item.ItemStack;

public class CreativeOxygenCanItem extends OxygenCanItem
{
	public CreativeOxygenCanItem(Properties properties)
	{
		super(properties.fireResistant());
	}

	@Override
	protected long getFluidCapacity()
	{
		return CreativeFluidContainer.CAPACITY;
	}

	@Override
	protected long getFluidTransfer()
	{
		return CreativeFluidContainer.CAPACITY;
	}

	@Override
	public WrappedItemFluidContainer getFluidContainer(ItemStack holder)
	{
		return new WrappedItemFluidContainer(holder, new CreativeFluidContainer(FluidPredicates::isOxygen));
	}

	@Override
	public IOxygenCharger getOxygenCharger(ItemStackHolder item)
	{
		return new AbstractOxygenCharger(item)
		{
			@Override
			public boolean canUseOnCold()
			{
				return true;
			}

			@Override
			public boolean canUseOnHot()
			{
				return true;
			}

		};

	}

}
