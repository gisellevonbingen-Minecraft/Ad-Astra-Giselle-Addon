package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigSeparator;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = ItemsConfig.ID, translation = ItemsConfig.PREFIX)
public final class ItemsConfig
{
	public static final String ID = "items";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;

	public static final String OXYGEN_CHARGERS_ID = "oxygen_chargers";
	public static final String OXYGEN_CHARGERS_PREFIX = PREFIX + "." + OXYGEN_CHARGERS_ID;
	@ConfigSeparator(translation = OXYGEN_CHARGERS_PREFIX)
	@ConfigEntry(id = OXYGEN_CHARGERS_ID + "_distribution_interval", type = EntryType.INTEGER, translation = OXYGEN_CHARGERS_PREFIX + "_distribution_interval")
	public static int OXYGEN_CHARGERS_DISTRUBUTION_INTERVAL = 20;

	public static final String OXYGEN_CAN_ID = "oxygen_can";
	public static final String OXYGEN_CAN_PREFIX = PREFIX + "." + OXYGEN_CAN_ID;
	@ConfigSeparator(translation = OXYGEN_CAN_PREFIX)
	@ConfigEntry(id = OXYGEN_CAN_ID + "_fluid_capacity", type = EntryType.LONG, translation = OXYGEN_CAN_PREFIX + "_fluid_capacity")
	public static long OXYGEN_CAN_FLUID_CAPACITY = 500L;
	@ConfigEntry(id = OXYGEN_CAN_ID + "_fluid_transfer", type = EntryType.LONG, translation = OXYGEN_CAN_PREFIX + "_fluid_transfer")
	public static long OXYGEN_CAN_FLUID_TRANSFER = 125L;

	public static final String NETHERITE_OXYGEN_CAN_ID = "netherite_oxygen_can";
	public static final String NETHERITE_OXYGEN_CAN_PREFIX = PREFIX + "." + NETHERITE_OXYGEN_CAN_ID;
	@ConfigSeparator(translation = NETHERITE_OXYGEN_CAN_PREFIX)
	@ConfigEntry(id = NETHERITE_OXYGEN_CAN_ID + "_fluid_capacity", type = EntryType.LONG, translation = NETHERITE_OXYGEN_CAN_PREFIX + "_fluid_capacity")
	public static long NETHERITE_OXYGEN_CAN_FLUID_CAPACITY = 1_000L;
	@ConfigEntry(id = NETHERITE_OXYGEN_CAN_ID + "_fluid_transfer", type = EntryType.LONG, translation = NETHERITE_OXYGEN_CAN_PREFIX + "_fluid_transfer")
	public static long NETHERITE_OXYGEN_CAN_FLUID_TRANSFER = 250L;
}
