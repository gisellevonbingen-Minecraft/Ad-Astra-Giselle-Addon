package ad_astra_giselle_addon.common.content.oxygen;

import earth.terrarium.common_storage_lib.context.ItemContext;

public interface IOxygenChargerItem extends IOxygenStorageItem
{
	IOxygenCharger getOxygenCharger(ItemContext context);

	@Override
	default IOxygenStorage getOxygenStorage(ItemContext context)
	{
		return this.getOxygenCharger(context);
	}

}
