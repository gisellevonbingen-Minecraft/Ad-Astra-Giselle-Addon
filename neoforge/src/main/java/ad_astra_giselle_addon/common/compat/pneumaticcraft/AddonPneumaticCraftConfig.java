package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigObject;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.config.ForgeCompatsConfig;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;

@SuppressWarnings("deprecation")
@Category(AddonPneumaticCraftConfig.ID)
@ConfigInfo(icon = "pneumaticcraft", title = "pneumaticcraft")
public final class AddonPneumaticCraftConfig
{
	public static final String ID = PneumaticCraftCompat.MOD_ID;
	public static final String PREFIX = ForgeCompatsConfig.PREFIX + "." + ID;
	public static final String UPGRADE_PREFIX = PREFIX + ".upgades_";

	@ConfigEntry(id = "upgades_space_breathing", type = EntryType.OBJECT, translation = UPGRADE_PREFIX + "space_breathing")
	public static final OxygenProofUpgradeConfigObject UPGRADES_OXYGEN_PROOF = new OxygenProofUpgradeConfigObject();

	@ConfigEntry(id = "upgades_space_fire_proof", type = EntryType.OBJECT, translation = UPGRADE_PREFIX + "space_fire_proof")
	public static final HotTemperatureProofUpgradeConfigObject UPGRADES_HOT_TEMPERATURE_PROOF = new HotTemperatureProofUpgradeConfigObject();

	@ConfigEntry(id = "upgades_acid_rain_proof", type = EntryType.OBJECT, translation = UPGRADE_PREFIX + "acid_rain_proof")
	public static final AcidRainProofUpgradeConfigObject UPGRADES_ACID_RAIN_PROOF = new AcidRainProofUpgradeConfigObject();

	@ConfigEntry(id = "upgades_gravity_normalizing", type = EntryType.OBJECT, translation = UPGRADE_PREFIX + "gravity_normalizing")
	public static final GravityProofUpgradeConfigObject UPGRADES_GRAVITY_PROOF = new GravityProofUpgradeConfigObject();

	@ConfigObject
	public static class OxygenProofUpgradeConfigObject
	{
		public static final String OXYGEN_PROOF_PREFIX = UPGRADE_PREFIX + "space_breathing";
		@ConfigEntry(id = "air_using", type = EntryType.INTEGER, translation = OXYGEN_PROOF_PREFIX + "_air_using")
		@Comment(value = "Air usage for provide oxygen " + EnchantmentsConfig.OXYGEN_ENERGY_INTERVAL_TOOLTIP, translation = OXYGEN_PROOF_PREFIX + "_air_using.comment")
		public int airUsing = 5 * ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;
	}

	@ConfigObject
	public static class HotTemperatureProofUpgradeConfigObject
	{
		public static final String HOT_TEMPERATURE_PROOF_PREFIX = UPGRADE_PREFIX + "space_fire_proof";
		@ConfigEntry(id = "air_using", type = EntryType.INTEGER, translation = HOT_TEMPERATURE_PROOF_PREFIX + "_air_using")
		@Comment(value = "Air usage for proof " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = HOT_TEMPERATURE_PROOF_PREFIX + "_air_using.comment")
		public int airUsing = 5 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
	}

	@ConfigObject
	public static class AcidRainProofUpgradeConfigObject
	{
		public static final String ACID_RAIN_PROOF_PREFIX = UPGRADE_PREFIX + "acid_rain_proof";
		@ConfigEntry(id = "air_using", type = EntryType.INTEGER, translation = ACID_RAIN_PROOF_PREFIX + "_air_using")
		@Comment(value = "Air usage for proof " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = ACID_RAIN_PROOF_PREFIX + "_air_using.comment")
		public int airUsing = 5 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
	}

	@ConfigObject
	public static class GravityProofUpgradeConfigObject
	{
		public static final String GRAVITY_PROOF_PREFIX = UPGRADE_PREFIX + "gravity_normalizing";
		@ConfigEntry(id = "air_using", type = EntryType.INTEGER, translation = GRAVITY_PROOF_PREFIX + "_air_using")
		@Comment(value = "Air usage for proof " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = GRAVITY_PROOF_PREFIX + "_air_using.comment")
		public int airUsing = 5 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
	}

}
