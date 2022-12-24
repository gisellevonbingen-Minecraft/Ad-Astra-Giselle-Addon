package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigSeparator;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

import ad_astra_giselle_addon.common.fluid.FluidHooks2;

@Category(id = MachinesConfig.ID, translation = MachinesConfig.PREFIX)
public final class MachinesConfig
{
	public static final String ID = "machines";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;

	public static final String FUEL_LOADER_ID = "fuel_loader";
	public static final String FUEL_LOADER_PREFIX = PREFIX + "." + FUEL_LOADER_ID;
	@ConfigSeparator(translation = FUEL_LOADER_PREFIX)
	@ConfigEntry(id = FUEL_LOADER_ID + "_fluid_capacity", type = EntryType.LONG, translation = FUEL_LOADER_PREFIX + "_fluid_capacity")
	public static long FUEL_LOADER_FLUID_CAPACITY = FluidHooks2.BUCKET * 8;
	@ConfigEntry(id = FUEL_LOADER_ID + "_fluid_transfer", type = EntryType.LONG, translation = FUEL_LOADER_PREFIX + "_fluid_transfer")
	public static long FUEL_LOADER_FLUID_TRANSFER = FluidHooks2.BUCKET / 4;
	@ConfigEntry(id = FUEL_LOADER_ID + "_working_range", type = EntryType.INTEGER, translation = FUEL_LOADER_PREFIX + "_working_range")
	@Comment(value = "Blocks from Fuel Loader to each direction", translation = FUEL_LOADER_PREFIX + "_working_range.comment")
	public static int FUEL_LOADER_WORKING_RANGE = 2;

	public static final String AUTOMATION_NASA_WORKBENCH_ID = "automation_nasa_workbench";
	public static final String AUTOMATION_NASA_WORKBENCH_PREFIX = PREFIX + "." + AUTOMATION_NASA_WORKBENCH_ID;
	@ConfigSeparator(translation = AUTOMATION_NASA_WORKBENCH_PREFIX)
	@ConfigEntry(id = AUTOMATION_NASA_WORKBENCH_ID + "_energy_capacity", type = EntryType.LONG, translation = AUTOMATION_NASA_WORKBENCH_PREFIX + "_energy_capacity")
	public static long AUTOMATION_NASA_WORKBENCH_ENERGY_CAPACITY = 9600L;
	@ConfigEntry(id = AUTOMATION_NASA_WORKBENCH_ID + "_energy_usage", type = EntryType.LONG, translation = AUTOMATION_NASA_WORKBENCH_PREFIX + "_energy_usage")
	public static long AUTOMATION_NASA_WORKBENCH_ENERGY_USAGE = 24L;
	@ConfigEntry(id = AUTOMATION_NASA_WORKBENCH_ID + "_cook_time", type = EntryType.INTEGER, translation = AUTOMATION_NASA_WORKBENCH_PREFIX + "_cook_time")
	public static int AUTOMATION_NASA_WORKBENCH_COOK_TIME = 200;
}
