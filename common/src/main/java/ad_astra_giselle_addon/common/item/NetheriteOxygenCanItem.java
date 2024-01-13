package ad_astra_giselle_addon.common.item;

import ad_astra_giselle_addon.common.config.ItemsConfig;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import earth.terrarium.botarium.common.item.ItemStackHolder;

public class NetheriteOxygenCanItem extends OxygenCanItem
{
	public NetheriteOxygenCanItem(Properties properties)
	{
		super(properties.fireResistant());
	}

	@Override
	protected long getFluidCapacity()
	{
		return ItemsConfig.NETHERITE_OXYGEN_CAN_FLUID_CAPACITY;
	}
	
	@Override
	protected long getFluidTransfer()
	{
		return ItemsConfig.NETHERITE_OXYGEN_CAN_FLUID_TRANSFER;
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
