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

	public static final String SPACE_BREATHING_ID = UPGRADE_ID + ".space_breathing";
	public static final String SPACE_BREATHING_PREFIX = PREFIX + "." + SPACE_BREATHING_ID;
	@ConfigSeparator(translation = SPACE_BREATHING_PREFIX)
	@ConfigEntry(id = SPACE_BREATHING_ID + ".air_using", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".air_using")
	@Comment(value = "Air usage for provide oxygen " + EnchantmentsConfig.OXYGEN_ENERGY_INTERVAL_TOOLTIP, translation = SPACE_BREATHING_PREFIX + ".air_using.comment")
	public static int SPACE_BREATHING_AIR_USING = 5 * ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;

	public static final String SPACE_FIRE_PROOF_ID = UPGRADE_ID + ".space_fire_proof";
	public static final String SPACE_FIRE_PROOF_PREFIX = PREFIX + "." + SPACE_FIRE_PROOF_ID;
	@ConfigSeparator(translation = SPACE_FIRE_PROOF_PREFIX)
	@ConfigEntry(id = SPACE_FIRE_PROOF_ID + ".air_using", type = EntryType.INTEGER, translation = SPACE_FIRE_PROOF_PREFIX + ".air_using")
	@Comment(value = "Air usage for proof " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = SPACE_FIRE_PROOF_PREFIX + ".air_using.comment")
	public static int SPACE_FIRE_PROOF_AIR_USING = 5 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String VENUS_ACID_PROOFID = UPGRADE_ID + ".venus_acid_proof";
	public static final String VENUS_ACID_PROOF_PREFIX = PREFIX + "." + VENUS_ACID_PROOFID;
	@ConfigSeparator(translation = VENUS_ACID_PROOF_PREFIX)
	@ConfigEntry(id = VENUS_ACID_PROOFID + ".air_using", type = EntryType.INTEGER, translation = VENUS_ACID_PROOF_PREFIX + ".air_using")
	@Comment(value = "Air usage for proof " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = VENUS_ACID_PROOF_PREFIX + ".air_using.comment")
	public static int VENUS_ACID_PROOF_AIR_USING = 5 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
}
