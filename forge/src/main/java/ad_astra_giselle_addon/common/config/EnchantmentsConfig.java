package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigSeparator;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = EnchantmentsConfig.ID, translation = EnchantmentsConfig.PREFIX)
public final class EnchantmentsConfig
{
	public static final String ID = "enchantments";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;

	@ConfigEntry(id = "tooltip_enabled", type = EntryType.BOOLEAN, translation = PREFIX + ".tooltip_enabled")
	@Comment(value = "Show tooltip on this mod's enchanted book", translation = PREFIX + ".tooltip_enabled.comment")
	public static boolean TOOLTIP_ENABLED = true;
	@ConfigEntry(id = "tooltip_ignore", type = EntryType.BOOLEAN, translation = PREFIX + ".tooltip_ignore")
	@Comment(value = "Tooltip will don't show when 'Enchantment Descriptions' or 'CoFH Core' installed.\nbut, if this set 'true' show tooltip with ignore that mods.", translation = PREFIX + ".tooltip_ignore.comment")
	public static boolean TOOLTIP_IGNORE = false;

	public static final String SPACE_BREATHING_ID = "space_breathing";
	public static final String SPACE_BREATHING_PREFIX = PREFIX + "." + SPACE_BREATHING_ID;
	@ConfigSeparator(translation = SPACE_BREATHING_PREFIX)
	@ConfigEntry(id = SPACE_BREATHING_ID + ".energy_using", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".energy_using")
	@Comment(value = "Energy usage for breath", translation = SPACE_BREATHING_PREFIX + ".energy_using.comment")
	public static int SPACE_BREATHING_ENERGY_USING = 10;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".energy_duration", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".energy_duration")
	@Comment(value = "Breath duration on using energy [tick]", translation = SPACE_BREATHING_PREFIX + ".energy_duration.comment")
	public static int SPACE_BREATHING_ENERGY_DURATION = 4;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".energy_oxygen", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".energy_oxygen")
	@Comment(value = "Oxygen usage on using energy", translation = SPACE_BREATHING_PREFIX + ".energy_oxygen.comment")
	public static int SPACE_BREATHING_ENERGY_OXYGEN = 1;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".durability_using", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".durability_using")
	@Comment(value = "Durability usage for breath", translation = SPACE_BREATHING_PREFIX + ".durability_using.comment")
	public static int SPACE_BREATHING_DURABILITY_USING = 1;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".durability_duration", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".durability_duration")
	@Comment(value = "Breath duration on using durability [tick]", translation = SPACE_BREATHING_PREFIX + ".durability_duration.comment")
	public static int SPACE_BREATHING_DURABILITY_DURATION = 60;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".durability_oxygen", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".durability_oxygen")
	@Comment(value = "Oxygen usage on using durability", translation = SPACE_BREATHING_PREFIX + ".durability_oxygen.comment")
	public static int SPACE_BREATHING_DURABILITY_OXYGEN = 15;

	public static final String SPACE_FIRE_PROOF_ID = "space_fire_proof";
	public static final String SPACE_FIRE_PROOF_PREFIX = PREFIX + "." + SPACE_FIRE_PROOF_ID;
	@ConfigSeparator(translation = SPACE_FIRE_PROOF_PREFIX)
	@ConfigEntry(id = SPACE_FIRE_PROOF_ID + ".energy_using", type = EntryType.INTEGER, translation = SPACE_FIRE_PROOF_PREFIX + ".energy_using")
	@Comment(value = "Energy usage for proof", translation = SPACE_FIRE_PROOF_PREFIX + ".energy_using.comment")
	public static int SPACE_FIRE_PROOF_ENERGY_USING = 10;
	@ConfigEntry(id = SPACE_FIRE_PROOF_ID + ".energy_duration", type = EntryType.INTEGER, translation = SPACE_FIRE_PROOF_PREFIX + ".energy_duration")
	@Comment(value = "Proof duration on using energy [tick]", translation = SPACE_FIRE_PROOF_PREFIX + ".energy_duration.comment")
	public static int SPACE_FIRE_PROOF_ENERGY_DURATION = 1;
	@ConfigEntry(id = SPACE_FIRE_PROOF_ID + ".durability_using", type = EntryType.INTEGER, translation = SPACE_FIRE_PROOF_PREFIX + ".durability_using")
	@Comment(value = "Durability usage for proof", translation = SPACE_FIRE_PROOF_PREFIX + ".durability_using.comment")
	public static int SPACE_FIRE_PROOF_DURABILITY_USING = 1;
	@ConfigEntry(id = SPACE_FIRE_PROOF_ID + ".durability_duration", type = EntryType.INTEGER, translation = SPACE_FIRE_PROOF_PREFIX + ".durability_duration")
	@Comment(value = "Proof duration on using durability [tick]", translation = SPACE_FIRE_PROOF_PREFIX + ".durability_duration.comment")
	public static int SPACE_FIRE_PROOF_DURABILITY_DURATION = 60;

	public static final String VENUS_ACID_PROOF_ID = "venus_acid_proof";
	public static final String VENUS_ACID_PROOF_PREFIX = PREFIX + "." + VENUS_ACID_PROOF_ID;
	@ConfigSeparator(translation = VENUS_ACID_PROOF_PREFIX)
	@ConfigEntry(id = VENUS_ACID_PROOF_ID + ".energy_using", type = EntryType.INTEGER, translation = VENUS_ACID_PROOF_PREFIX + ".energy_using")
	@Comment(value = "Energy usage for proof", translation = VENUS_ACID_PROOF_PREFIX + ".energy_using.comment")
	public static int VENUS_ACID_PROOF_ENERGY_USING = 10;
	@ConfigEntry(id = VENUS_ACID_PROOF_ID + ".energy_duration", type = EntryType.INTEGER, translation = VENUS_ACID_PROOF_PREFIX + ".energy_duration")
	@Comment(value = "Proof duration on using energy [tick]", translation = VENUS_ACID_PROOF_PREFIX + ".energy_duration.comment")
	public static int VENUS_ACID_PROOF_ENERGY_DURATION = 1;
	@ConfigEntry(id = VENUS_ACID_PROOF_ID + ".durability_using", type = EntryType.INTEGER, translation = VENUS_ACID_PROOF_PREFIX + ".durability_using")
	@Comment(value = "Durability usage for proof", translation = VENUS_ACID_PROOF_PREFIX + ".durability_using.comment")
	public static int VENUS_ACID_PROOF_DURABILITY_USING = 1;
	@ConfigEntry(id = VENUS_ACID_PROOF_ID + ".durability_duration", type = EntryType.INTEGER, translation = VENUS_ACID_PROOF_PREFIX + ".durability_duration")
	@Comment(value = "Proof duration on using durability [tick]", translation = VENUS_ACID_PROOF_PREFIX + ".durability_duration.comment")
	public static int VENUS_ACID_PROOF_DURABILITY_DURATION = 60;
}
