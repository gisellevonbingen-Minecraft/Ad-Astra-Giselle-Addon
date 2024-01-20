package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigSeparator;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;

@Category(id = EnchantmentsConfig.ID, translation = EnchantmentsConfig.PREFIX)
public final class EnchantmentsConfig
{
	public static final String ID = "enchantments";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;
	public static final String GENERAL_ENERGY_INTERVAL_TOOLTIP = "[in every " + ProofAbstractUtils.GENERAL_PROOF_INTERVAL + " ticks]";
	public static final String GENERAL_DURABILITY_TICKS_TOOLTIP = "[ticks, be multiple of " + ProofAbstractUtils.GENERAL_PROOF_INTERVAL + "]";
	public static final String OXYGEN_ENERGY_INTERVAL_TOOLTIP = "[in every " + ProofAbstractUtils.OXYGEN_PROOF_INTERVAL + " ticks]";
	public static final String OXYGEN_DURABILITY_TICKS_TOOLTIP = "[ticks, be multiple of " + ProofAbstractUtils.OXYGEN_PROOF_INTERVAL + "]";

	@ConfigEntry(id = "tooltip_enabled", type = EntryType.BOOLEAN, translation = PREFIX + ".tooltip_enabled")
	@Comment(value = "Show tooltip on this mod's enchanted book", translation = PREFIX + ".tooltip_enabled.comment")
	public static boolean TOOLTIP_ENABLED = true;
	@ConfigEntry(id = "tooltip_ignore", type = EntryType.BOOLEAN, translation = PREFIX + ".tooltip_ignore")
	@Comment(value = "Tooltip will don't show when 'Enchantment Descriptions' or 'Enchantment Lore' or 'CoFH Core' installed.\nbut, if this set 'true' show tooltip with ignore that mods.", translation = PREFIX + ".tooltip_ignore.comment")
	public static boolean TOOLTIP_IGNORE = false;

	public static final String OXYGEN_PROOF_ID = "space_breathing";
	public static final String OXYGEN_PROOF_PREFIX = PREFIX + "." + OXYGEN_PROOF_ID;
	@ConfigSeparator(translation = OXYGEN_PROOF_PREFIX)
	@ConfigEntry(id = OXYGEN_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = OXYGEN_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for breath " + OXYGEN_ENERGY_INTERVAL_TOOLTIP, translation = OXYGEN_PROOF_PREFIX + "_energy_using.comment")
	public static int OXYGEN_PROOF_ENERGY_USING = 30;
	@ConfigEntry(id = OXYGEN_PROOF_ID + "_durability_oxygen", type = EntryType.LONG, translation = OXYGEN_PROOF_PREFIX + "_durability_oxygen")
	@Comment(value = "Oxygen usage on using durability", translation = OXYGEN_PROOF_PREFIX + "_durability_oxygen.comment")
	public static long OXYGEN_PROOF_DURABILITY_OXYGEN = 2L;
	@ConfigEntry(id = OXYGEN_PROOF_ID + "_durability_using", type = EntryType.INTEGER, translation = OXYGEN_PROOF_PREFIX + "_durability_using")
	@Comment(value = "Durability usage for breath", translation = OXYGEN_PROOF_PREFIX + "_durability_using.comment")
	public static int OXYGEN_PROOF_DURABILITY_USING = 1;
	@ConfigEntry(id = OXYGEN_PROOF_ID + "_durability_duration", type = EntryType.INTEGER, translation = OXYGEN_PROOF_PREFIX + "_durability_duration")
	@Comment(value = "Breath duration on using durability " + OXYGEN_DURABILITY_TICKS_TOOLTIP, translation = OXYGEN_PROOF_PREFIX + "_durability_duration.comment")
	public static int OXYGEN_PROOF_DURABILITY_DURATION = 2 * ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;

	public static final String HOT_TEMPERATURE_PROOF_ID = "space_fire_proof";
	public static final String HOT_TEMPERATURE_PROOF_PREFIX = PREFIX + "." + HOT_TEMPERATURE_PROOF_ID;
	@ConfigSeparator(translation = HOT_TEMPERATURE_PROOF_PREFIX)
	@ConfigEntry(id = HOT_TEMPERATURE_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = HOT_TEMPERATURE_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for proof " + GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = HOT_TEMPERATURE_PROOF_PREFIX + "_energy_using.comment")
	public static int HOT_TEMPERATURE_PROOF_ENERGY_USING = 10;
	@ConfigEntry(id = HOT_TEMPERATURE_PROOF_ID + "_durability_using", type = EntryType.INTEGER, translation = HOT_TEMPERATURE_PROOF_PREFIX + "_durability_using")
	@Comment(value = "Durability usage for proof", translation = HOT_TEMPERATURE_PROOF_PREFIX + "_durability_using.comment")
	public static int HOT_TEMPERATURE_PROOF_DURABILITY_USING = 1;
	@ConfigEntry(id = HOT_TEMPERATURE_PROOF_ID + "_durability_duration", type = EntryType.INTEGER, translation = HOT_TEMPERATURE_PROOF_PREFIX + "_durability_duration")
	@Comment(value = "Proof duration on using durability " + GENERAL_DURABILITY_TICKS_TOOLTIP, translation = HOT_TEMPERATURE_PROOF_PREFIX + "_durability_duration.comment")
	public static int HOT_TEMPERATURE_PROOF_DURABILITY_DURATION = 6 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String ACID_RAIN_PROOF_ID = "acid_rain_proof";
	public static final String ACID_RAIN_PROOF_PREFIX = PREFIX + "." + ACID_RAIN_PROOF_ID;
	@ConfigSeparator(translation = ACID_RAIN_PROOF_PREFIX)
	@ConfigEntry(id = ACID_RAIN_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = ACID_RAIN_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for proof " + GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = ACID_RAIN_PROOF_PREFIX + "_energy_using.comment")
	public static int ACID_RAIN_PROOF_ENERGY_USING = 10;
	@ConfigEntry(id = ACID_RAIN_PROOF_ID + "_durability_using", type = EntryType.INTEGER, translation = ACID_RAIN_PROOF_PREFIX + "_durability_using")
	@Comment(value = "Durability usage for proof", translation = ACID_RAIN_PROOF_PREFIX + "_durability_using.comment")
	public static int ACID_RAIN_PROOF_DURABILITY_USING = 1;
	@ConfigEntry(id = ACID_RAIN_PROOF_ID + "_durability_duration", type = EntryType.INTEGER, translation = ACID_RAIN_PROOF_PREFIX + "_durability_duration")
	@Comment(value = "Proof duration on using durability " + GENERAL_DURABILITY_TICKS_TOOLTIP, translation = ACID_RAIN_PROOF_PREFIX + "_durability_duration.comment")
	public static int ACID_RAIN_PROOF_DURABILITY_DURATION = 6 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String GRAVITY_PROOF_ID = "gravity_normalizing";
	public static final String GRAVITY_PROOF_PREFIX = PREFIX + "." + GRAVITY_PROOF_ID;
	@ConfigSeparator(translation = GRAVITY_PROOF_PREFIX)
	@ConfigEntry(id = GRAVITY_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = GRAVITY_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for proof " + GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = GRAVITY_PROOF_PREFIX + "_energy_using.comment")
	public static int GRAVITY_PROOF_ENERGY_USING = 10;
	@ConfigEntry(id = GRAVITY_PROOF_ID + "_durability_using", type = EntryType.INTEGER, translation = GRAVITY_PROOF_PREFIX + "_durability_using")
	@Comment(value = "Durability usage for proof", translation = GRAVITY_PROOF_PREFIX + "_durability_using.comment")
	public static int GRAVITY_PROOF_DURABILITY_USING = 1;
	@ConfigEntry(id = GRAVITY_PROOF_ID + "_durability_duration", type = EntryType.INTEGER, translation = GRAVITY_PROOF_PREFIX + "_durability_duration")
	@Comment(value = "Proof duration on using durability " + GENERAL_DURABILITY_TICKS_TOOLTIP, translation = GRAVITY_PROOF_PREFIX + "_durability_duration.comment")
	public static int GRAVITY_PROOF_DURABILITY_DURATION = 6 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
}
