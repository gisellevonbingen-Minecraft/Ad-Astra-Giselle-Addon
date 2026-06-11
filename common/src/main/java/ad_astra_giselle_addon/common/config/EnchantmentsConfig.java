package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigObject;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;

@SuppressWarnings("deprecation")
@Category(EnchantmentsConfig.ID)
@ConfigInfo(icon = "enchantments", title = "Enchantments Config")
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

	@ConfigEntry(id = "space_fire_proof", type = EntryType.OBJECT, translation = PREFIX + ".space_fire_proof")
	public static final ProofConfigObject HOT_TEMPERATURE_PROOF = new ProofConfigObject();

	@ConfigEntry(id = "acid_rain_proof", type = EntryType.OBJECT, translation = PREFIX + ".acid_rain_proof")
	public static final ProofConfigObject ACID_RAIN_PROOF = new ProofConfigObject();

	@ConfigEntry(id = "gravity_normalizing", type = EntryType.OBJECT, translation = PREFIX + ".gravity_normalizing")
	public static final ProofConfigObject GRAVITY_PROOF = new ProofConfigObject();

	@ConfigObject
	public static class ProofConfigObject
	{
		public static final String PROOF_PREFIX = PREFIX + ".proof";
		@ConfigEntry(id = "energy_using", type = EntryType.INTEGER, translation = PROOF_PREFIX + "_energy_using")
		@Comment(value = "Energy usage for proof " + GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = PROOF_PREFIX + "_energy_using.comment")
		public int energyUsing = 10;
		@ConfigEntry(id = "durability_using", type = EntryType.INTEGER, translation = PROOF_PREFIX + "_durability_using")
		@Comment(value = "Durability usage for proof", translation = PROOF_PREFIX + "_durability_using.comment")
		public int durabilityUsing = 1;
		@ConfigEntry(id = "durability_duration", type = EntryType.INTEGER, translation = PROOF_PREFIX + "_durability_duration")
		@Comment(value = "Proof duration on using durability " + GENERAL_DURABILITY_TICKS_TOOLTIP, translation = PROOF_PREFIX + "_durability_duration.comment")
		public int durabilityDuration = 6 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
	}

}
