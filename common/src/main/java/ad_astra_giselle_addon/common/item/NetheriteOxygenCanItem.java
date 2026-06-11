package ad_astra_giselle_addon.common.item;

import ad_astra_giselle_addon.common.config.ItemsConfig;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import earth.terrarium.common_storage_lib.context.ItemContext;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;

public class NetheriteOxygenCanItem extends OxygenCanItem
{
	public NetheriteOxygenCanItem(Properties properties)
	{
		super(properties.fireResistant());
	}

	@Override
	protected long getFluidCapacity()
	{
		return FluidAmounts.toPlatformAmount(ItemsConfig.NETHERITE_OXYGEN_CAN.fluidCapacity);
	}

	@Override
	protected long getFluidTransfer()
	{
		return FluidAmounts.toPlatformAmount(ItemsConfig.NETHERITE_OXYGEN_CAN.fluidTransfer);
	}

	@Override
	public IOxygenCharger getOxygenCharger(ItemContext context)
	{
		return new AbstractOxygenCharger(context)
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
