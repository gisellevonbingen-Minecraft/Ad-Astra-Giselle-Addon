package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.content.oxygen.IChargeMode;

public class AddonDataComponentTypes
{
	public static final DataComponentTypeRegistryCollection DATA_COMPONENT_TYPES = new DataComponentTypeRegistryCollection(AdAstraGiselleAddon.MOD_ID);
	public static final DataComponentTypeRegistryHolder<IChargeMode> CHARGE_MODE = DATA_COMPONENT_TYPES.add("charge_mode", builder -> builder.persistent(IChargeMode.CODEC).networkSynchronized(IChargeMode.STREAM_CODEC));

	private AddonDataComponentTypes()
	{

	}

}
