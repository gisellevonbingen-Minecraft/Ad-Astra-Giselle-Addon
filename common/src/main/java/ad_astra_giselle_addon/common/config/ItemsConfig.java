package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigObject;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

@SuppressWarnings("deprecation")
@Category(ItemsConfig.ID)
@ConfigInfo(icon = "items", title = "Items Config")
public final class ItemsConfig
{
	public static final String ID = "items";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;

	@ConfigEntry(id = "oxygen_chargers", type = EntryType.OBJECT, translation = PREFIX + ".oxygen_chargers")
	public static final OxygenChargersConfigObject OXYGEN_CHARGERS = new OxygenChargersConfigObject();

	@ConfigEntry(id = "oxygen_can", type = EntryType.OBJECT, translation = PREFIX + ".oxygen_can")
	public static final OxygenCanConfigObject OXYGEN_CAN = new OxygenCanConfigObject(500L, 125L);

	@ConfigEntry(id = "netherite_oxygen_can", type = EntryType.OBJECT, translation = PREFIX + ".netherite_oxygen_can")
	public static final OxygenCanConfigObject NETHERITE_OXYGEN_CAN = new OxygenCanConfigObject(1_000L, 250L);

	@ConfigObject
	public static class OxygenChargersConfigObject
	{
		public static final String OXYGEN_CHARGERS_PREFIX = PREFIX + ".oxygen_chargers";
		@ConfigEntry(id = "distribution_interval", type = EntryType.INTEGER, translation = OXYGEN_CHARGERS_PREFIX + "_distribution_interval")
		public int distributionInterval = 10;
	}

	@ConfigObject
	public static class OxygenCanConfigObject
	{
		public static final String OXYGEN_CAN_PREFIX = PREFIX + ".oxygen_can";
		@ConfigEntry(id = "fluid_capacity", type = EntryType.LONG, translation = OXYGEN_CAN_PREFIX + "_fluid_capacity")
		public long fluidCapacity = 0L;
		@ConfigEntry(id = "fluid_transfer", type = EntryType.LONG, translation = OXYGEN_CAN_PREFIX + "_fluid_transfer")
		public long fluidTransfer = 0L;

		public OxygenCanConfigObject(long fluidCapacity, long fluidTransfer)
		{
			this.fluidCapacity = fluidCapacity;
			this.fluidTransfer = fluidTransfer;
		}

	}

}
