package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigSeparator;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

import earth.terrarium.botarium.api.fluid.FluidHooks;

@Category(id = MachinesConfig.ID, translation = MachinesConfig.PREFIX)
public final class MachinesConfig
{
	public static final String ID = "machines";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;

	public static final String FUEL_LOADER_ID = "fuel_loader";
	public static final String FUEL_LOADER_PREFIX = PREFIX + "." + FUEL_LOADER_ID;
	@ConfigSeparator(translation = FUEL_LOADER_PREFIX)
	@ConfigEntry(id = FUEL_LOADER_ID + "_fluid_capacity", type = EntryType.LONG, translation = FUEL_LOADER_PREFIX + "_fluid_capacity")
	public static long FUEL_LOADER_FLUID_CAPACITY = FluidHooks.buckets(8);
	@ConfigEntry(id = FUEL_LOADER_ID + "_fluid_transfer", type = EntryType.LONG, translation = FUEL_LOADER_PREFIX + "_fluid_transfer")
	public static long FUEL_LOADER_FLUID_TRANSFER = FluidHooks.buckets(1) / 4;
	@ConfigEntry(id = FUEL_LOADER_ID + "_working_range", type = EntryType.INTEGER, translation = FUEL_LOADER_PREFIX + "_working_range")
	@Comment(value = "Blocks from Fuel Loader to each direction", translation = FUEL_LOADER_PREFIX + "_working_range.comment")
	public static int FUEL_LOADER_WORKING_RANGE = 2;
}
