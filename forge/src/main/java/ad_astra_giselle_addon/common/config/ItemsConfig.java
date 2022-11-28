package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.LongRange;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

import earth.terrarium.botarium.api.fluid.FluidHooks;

@Category(id = ItemsConfig.ID, translation = ItemsConfig.PREFIX)
public final class ItemsConfig
{
	public static final String ID = "items";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;

	public static final String OXYGEN_CAN_ID = "oxygen_can";
	public static final String OXYGEN_CAN_PREFIX = PREFIX + "." + OXYGEN_CAN_ID;
	@ConfigEntry(id = OXYGEN_CAN_ID + ".fluid_capacity", type = EntryType.LONG, translation = OXYGEN_CAN_PREFIX + ".fluid_capacity")
	@LongRange(min = 0, max = Long.MAX_VALUE)
	public static long OXYGEN_CAN_FLUID_CAPACITY = FluidHooks.buckets(2);
	@ConfigEntry(id = OXYGEN_CAN_ID + ".fluid_transfer", type = EntryType.LONG, translation = OXYGEN_CAN_PREFIX + ".fluid_transfer")
	@LongRange(min = 0, max = Long.MAX_VALUE)
	public static long OXYGEN_CAN_FLUID_TRANSFER = FluidHooks.toMillibuckets(256);
}
