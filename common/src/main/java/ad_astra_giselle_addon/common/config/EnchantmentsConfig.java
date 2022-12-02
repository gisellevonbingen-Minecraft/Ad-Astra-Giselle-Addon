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
	@Comment(value = "Tooltip will don't show when 'Enchantment Descriptions' or 'CoFH Core' installed.\nbut, if this set 'true' show tooltip with ignore that mods.", translation = PREFIX + ".tooltip_ignore.comment")
	public static boolean TOOLTIP_IGNORE = false;

	public static final String SPACE_BREATHING_ID = "space_breathing";
	public static final String SPACE_BREATHING_PREFIX = PREFIX + "." + SPACE_BREATHING_ID;
	@ConfigSeparator(translation = SPACE_BREATHING_PREFIX)
	@ConfigEntry(id = SPACE_BREATHING_ID + ".energy_using", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".energy_using")
	@Comment(value = "Energy usage for breath " + OXYGEN_ENERGY_INTERVAL_TOOLTIP, translation = SPACE_BREATHING_PREFIX + ".energy_using.comment")
	public static int SPACE_BREATHING_ENERGY_USING = 30;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".durability_oxygen", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".durability_oxygen")
	@Comment(value = "Oxygen usage on using durability", translation = SPACE_BREATHING_PREFIX + ".durability_oxygen.comment")
	public static int SPACE_BREATHING_DURABILITY_OXYGEN = 2;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".durability_using", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".durability_using")
	@Comment(value = "Durability usage for breath", translation = SPACE_BREATHING_PREFIX + ".durability_using.comment")
	public static int SPACE_BREATHING_DURABILITY_USING = 1;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".durability_duration", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".durability_duration")
	@Comment(value = "Breath duration on using durability " + OXYGEN_DURABILITY_TICKS_TOOLTIP, translation = SPACE_BREATHING_PREFIX + ".durability_duration.comment")
	public static int SPACE_BREATHING_DURABILITY_DURATION = SPACE_BREATHING_DURABILITY_OXYGEN * ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;

	public static final String SPACE_FIRE_PROOF_ID = "space_fire_proof";
	public static final String SPACE_FIRE_PROOF_PREFIX = PREFIX + "." + SPACE_FIRE_PROOF_ID;
	@ConfigSeparator(translation = SPACE_FIRE_PROOF_PREFIX)
	@ConfigEntry(id = SPACE_FIRE_PROOF_ID + ".energy_using", type = EntryType.INTEGER, translation = SPACE_FIRE_PROOF_PREFIX + ".energy_using")
	@Comment(value = "Energy usage for proof " + GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = SPACE_FIRE_PROOF_PREFIX + ".energy_using.comment")
	public static int SPACE_FIRE_PROOF_ENERGY_USING = 10;
	@ConfigEntry(id = SPACE_FIRE_PROOF_ID + ".durability_using", type = EntryType.INTEGER, translation = SPACE_FIRE_PROOF_PREFIX + ".durability_using")
	@Comment(value = "Durability usage for proof", translation = SPACE_FIRE_PROOF_PREFIX + ".durability_using.comment")
	public static int SPACE_FIRE_PROOF_DURABILITY_USING = 1;
	@ConfigEntry(id = SPACE_FIRE_PROOF_ID + ".durability_duration", type = EntryType.INTEGER, translation = SPACE_FIRE_PROOF_PREFIX + ".durability_duration")
	@Comment(value = "Proof duration on using durability " + GENERAL_DURABILITY_TICKS_TOOLTIP, translation = SPACE_FIRE_PROOF_PREFIX + ".durability_duration.comment")
	public static int SPACE_FIRE_PROOF_DURABILITY_DURATION = 6 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String VENUS_ACID_PROOF_ID = "venus_acid_proof";
	public static final String VENUS_ACID_PROOF_PREFIX = PREFIX + "." + VENUS_ACID_PROOF_ID;
	@ConfigSeparator(translation = VENUS_ACID_PROOF_PREFIX)
	@ConfigEntry(id = VENUS_ACID_PROOF_ID + ".energy_using", type = EntryType.INTEGER, translation = VENUS_ACID_PROOF_PREFIX + ".energy_using")
	@Comment(value = "Energy usage for proof " + GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = VENUS_ACID_PROOF_PREFIX + ".energy_using.comment")
	public static int VENUS_ACID_PROOF_ENERGY_USING = 10;
	@ConfigEntry(id = VENUS_ACID_PROOF_ID + ".durability_using", type = EntryType.INTEGER, translation = VENUS_ACID_PROOF_PREFIX + ".durability_using")
	@Comment(value = "Durability usage for proof", translation = VENUS_ACID_PROOF_PREFIX + ".durability_using.comment")
	public static int VENUS_ACID_PROOF_DURABILITY_USING = 1;
	@ConfigEntry(id = VENUS_ACID_PROOF_ID + ".durability_duration", type = EntryType.INTEGER, translation = VENUS_ACID_PROOF_PREFIX + ".durability_duration")
	@Comment(value = "Proof duration on using durability " + GENERAL_DURABILITY_TICKS_TOOLTIP, translation = VENUS_ACID_PROOF_PREFIX + ".durability_duration.comment")
	public static int VENUS_ACID_PROOF_DURABILITY_DURATION = 6 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
}
