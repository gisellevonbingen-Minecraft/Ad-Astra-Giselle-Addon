package ad_astra_giselle_addon.common.item;

import org.apache.commons.lang3.Range;

import ad_astra_giselle_addon.common.config.ItemsConfig;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import earth.terrarium.ad_astra.common.item.armor.SpaceSuit;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.botarium.api.item.ItemStackHolder;

public class NetheriteOxygenCanItem extends OxygenCanItem
{
	public NetheriteOxygenCanItem(Properties properties)
	{
		super(properties.fireResistant());
	}

	@Override
	public long getTankSize()
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
			public Range<Integer> getTemperatureThreshold()
			{
				return ((SpaceSuit) ModItems.NETHERITE_SPACE_SUIT.get()).getTemperatureThreshold();
			}

		};

	}

}
