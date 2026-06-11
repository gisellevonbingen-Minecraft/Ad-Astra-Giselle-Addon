package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigObject;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

@SuppressWarnings("deprecation")
@Category(MachinesConfig.ID)
@ConfigInfo(icon = "machines", title = "Machines Config")
public final class MachinesConfig
{
	public static final String ID = "machines";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;

	@ConfigEntry(id = "fuel_loader", type = EntryType.OBJECT, translation = PREFIX + ".fuel_loader")
	public static final FuelLoaderConfigObject FUEL_LOADER = new FuelLoaderConfigObject();

	@ConfigEntry(id = "automation_nasa_workbench", type = EntryType.OBJECT, translation = PREFIX + ".automation_nasa_workbench")
	public static final AutomationNASAWorkbenchConfigObject AUTOMATION_NASA_WORKBENCH = new AutomationNASAWorkbenchConfigObject();

	@ConfigEntry(id = "gravity_normalizer", type = EntryType.OBJECT, translation = PREFIX + ".gravity_normalizer")
	public static final GravityNormalizerConfigObject GRAVITY_NORMALIZER = new GravityNormalizerConfigObject();

	@ConfigEntry(id = "rocket_sensor", type = EntryType.OBJECT, translation = PREFIX + ".rocket_sensor")
	public static final RocketSensorConfigObject ROCKET_SENSOR = new RocketSensorConfigObject();

	@ConfigObject
	public static class FuelLoaderConfigObject
	{
		public static final String FUEL_LOADER_PREFIX = PREFIX + ".fuel_loader";
		@ConfigEntry(id = "fluid_capacity", type = EntryType.LONG, translation = FUEL_LOADER_PREFIX + "_fluid_capacity")
		public long fluidCapacity = 8_000L;
		@ConfigEntry(id = "fluid_transfer", type = EntryType.LONG, translation = FUEL_LOADER_PREFIX + "_fluid_transfer")
		public long fluidTransfer = 250L;
		@ConfigEntry(id = "working_range", type = EntryType.INTEGER, translation = FUEL_LOADER_PREFIX + "_working_range")
		@Comment(value = "Blocks from Fuel Loader to each direction", translation = FUEL_LOADER_PREFIX + "_working_range.comment")
		public int workingRange = 2;
	}

	@ConfigObject
	public static class AutomationNASAWorkbenchConfigObject
	{
		public static final String AUTOMATION_NASA_WORKBENCH_PREFIX = PREFIX + ".automation_nasa_workbench";
		@ConfigEntry(id = "energy_capacity", type = EntryType.LONG, translation = AUTOMATION_NASA_WORKBENCH_PREFIX + "_energy_capacity")
		public long energyCapacity = 9_600L;
		@ConfigEntry(id = "energy_usage", type = EntryType.LONG, translation = AUTOMATION_NASA_WORKBENCH_PREFIX + "_energy_usage")
		public long energyUsage = 24L;
		@ConfigEntry(id = "cook_time", type = EntryType.INTEGER, translation = AUTOMATION_NASA_WORKBENCH_PREFIX + "_cook_time")
		public int cookTime = 200;
	}

	@ConfigObject
	public static class GravityNormalizerConfigObject
	{
		public static final String GRAVITY_NORMALIZER_PREFIX = PREFIX + ".gravity_normalizer";
		@ConfigEntry(id = "energy_capacity", type = EntryType.LONG, translation = GRAVITY_NORMALIZER_PREFIX + "_energy_capacity")
		public long energyCapacity = 9_600L;
		@ConfigEntry(id = "energy_per_blocks", type = EntryType.DOUBLE, translation = GRAVITY_NORMALIZER_PREFIX + "_energy_per_blocks")
		@Comment(value = "Blocks = x * y * z", translation = GRAVITY_NORMALIZER_PREFIX + "_energy_per_blocks.comment")
		public double energyPerBlocks = 0.5D;
		@ConfigEntry(id = "max_length", type = EntryType.INTEGER, translation = GRAVITY_NORMALIZER_PREFIX + "_max_length")
		public int maxLength = 31;
		@ConfigEntry(id = "proof_duration", type = EntryType.INTEGER, translation = GRAVITY_NORMALIZER_PREFIX + "_proof_duration")
		public int proofDuration = 10;
	}

	@ConfigObject
	public static class RocketSensorConfigObject
	{
		public static final String ROCKET_SENSOR_PREFIX = PREFIX + ".rocket_sensor";
		@ConfigEntry(id = "working_range", type = EntryType.INTEGER, translation = ROCKET_SENSOR_PREFIX + "_working_range")
		@Comment(value = "Blocks from Rocket Sensor to each direction", translation = ROCKET_SENSOR_PREFIX + "_working_range.comment")
		public int workingRange = 2;
	}

}
