package ad_astra_giselle_addon.common.item;

import org.apache.commons.lang3.Range;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.fluid.CreativeItemFluidContainer;
import earth.terrarium.botarium.api.fluid.ItemFluidContainer;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.item.ItemStack;

public class CreativeOxygenCanItem extends OxygenCanItem
{
	public CreativeOxygenCanItem(Properties properties)
	{
		super(properties.fireResistant());
	}

	@Override
	public long getTankSize()
	{
		return CreativeItemFluidContainer.CAPACITY;
	}

	@Override
	protected long getFluidTransfer()
	{
		return CreativeItemFluidContainer.CAPACITY;
	}

	@Override
	public ItemFluidContainer getFluidContainer(ItemStack stack)
	{
		return new CreativeItemFluidContainer(stack, this.getFilter());
	}

	@Override
	public IOxygenCharger getOxygenCharger(ItemStackHolder item)
	{
		return new AbstractOxygenCharger(item)
		{
			@Override
			public Range<Integer> getTemperatureThreshold()
			{
				return null;
			}

		};

	}

}
