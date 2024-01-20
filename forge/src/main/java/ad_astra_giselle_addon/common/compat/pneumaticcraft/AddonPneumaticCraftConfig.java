package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigSeparator;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.config.ForgeCompatsConfig;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;

@Category(id = AddonPneumaticCraftConfig.ID, translation = AddonPneumaticCraftConfig.PREFIX)
public final class AddonPneumaticCraftConfig
{
	public static final String ID = PneumaticCraftCompat.MOD_ID;
	public static final String PREFIX = ForgeCompatsConfig.PREFIX + "." + ID;
	public static final String UPGRADE_ID = "upgades";

	public static final String OXYGEN_PROOF_ID = UPGRADE_ID + "_space_breathing";
	public static final String OXYGEN_PROOF_PREFIX = PREFIX + "." + OXYGEN_PROOF_ID;
	@ConfigSeparator(translation = OXYGEN_PROOF_PREFIX)
	@ConfigEntry(id = OXYGEN_PROOF_ID + "_air_using", type = EntryType.INTEGER, translation = OXYGEN_PROOF_PREFIX + "_air_using")
	@Comment(value = "Air usage for provide oxygen " + EnchantmentsConfig.OXYGEN_ENERGY_INTERVAL_TOOLTIP, translation = OXYGEN_PROOF_PREFIX + "_air_using.comment")
	public static int OXYGEN_PROOF_AIR_USING = 5 * ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;

	public static final String HOT_TEMPERATURE_PROOF_ID = UPGRADE_ID + "_space_fire_proof";
	public static final String HOT_TEMPERATURE_PROOF_PREFIX = PREFIX + "." + HOT_TEMPERATURE_PROOF_ID;
	@ConfigSeparator(translation = HOT_TEMPERATURE_PROOF_PREFIX)
	@ConfigEntry(id = HOT_TEMPERATURE_PROOF_ID + "_air_using", type = EntryType.INTEGER, translation = HOT_TEMPERATURE_PROOF_PREFIX + "_air_using")
	@Comment(value = "Air usage for proof " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = HOT_TEMPERATURE_PROOF_PREFIX + "_air_using.comment")
	public static int HOT_TEMPERATURE_PROOF_AIR_USING = 5 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String ACID_RAIN_PROOF_ID = UPGRADE_ID + "_acid_rain_proof";
	public static final String ACID_RAIN_PROOF_PREFIX = PREFIX + "." + ACID_RAIN_PROOF_ID;
	@ConfigSeparator(translation = ACID_RAIN_PROOF_PREFIX)
	@ConfigEntry(id = ACID_RAIN_PROOF_ID + "_air_using", type = EntryType.INTEGER, translation = ACID_RAIN_PROOF_PREFIX + "_air_using")
	@Comment(value = "Air usage for proof " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = ACID_RAIN_PROOF_PREFIX + "_air_using.comment")
	public static int ACID_RAIN_PROOF_AIR_USING = 5 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String GRAVITY_PROOF_ID = UPGRADE_ID + "_gravity_normalizing";
	public static final String GRAVITY_PROOF_PREFIX = PREFIX + "." + GRAVITY_PROOF_ID;
	@ConfigSeparator(translation = GRAVITY_PROOF_PREFIX)
	@ConfigEntry(id = GRAVITY_PROOF_ID + "_air_using", type = EntryType.INTEGER, translation = GRAVITY_PROOF_PREFIX + "_air_using")
	@Comment(value = "Air usage for proof " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = GRAVITY_PROOF_PREFIX + "_air_using.comment")
	public static int GRAVITY_PROOF_AIR_USING = 5 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
}
