package ad_astra_giselle_addon.common.compat.mekanism;

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
@Category(AddonMekanismConfig.ID)
@ConfigInfo(icon = "mekanism", title = "Mekanism")
public final class AddonMekanismConfig
{
	public static final String ID = MekanismCompat.MOD_ID;
	public static final String PREFIX = ForgeCompatsConfig.PREFIX + "." + ID;
	public static final String MODULES_PREFIX = PREFIX + ".modules_";

	@ConfigEntry(id = "modules_space_breathing", type = EntryType.OBJECT, translation = MODULES_PREFIX + "space_breathing")
	public static final OxygenProofModuleConfigObject MODULES_OXYGEN_PROOF = new OxygenProofModuleConfigObject();

	@ConfigEntry(id = "modules_space_fire_proof", type = EntryType.OBJECT, translation = MODULES_PREFIX + "space_fire_proof")
	public static final HotTemperatureProofModuleConfigObject MODULES_HOT_TEMPERATURE_PROOF = new HotTemperatureProofModuleConfigObject();

	@ConfigEntry(id = "modules_acid_rain_proof", type = EntryType.OBJECT, translation = MODULES_PREFIX + "acid_rain_proof")
	public static final AcidRainProofModuleConfigObject MODULES_ACID_RAIN_PROOF = new AcidRainProofModuleConfigObject();

	@ConfigEntry(id = "modules_gravity_normalizing", type = EntryType.OBJECT, translation = MODULES_PREFIX + "gravity_normalizing")
	public static final GravityProofModuleConfigObject MODULES_GRAVITY_PROOF = new GravityProofModuleConfigObject();

	@ConfigObject
	public static class OxygenProofModuleConfigObject
	{
		public static final String MODULES_OXYGEN_PROOF_PREFIX = MODULES_PREFIX + "space_breathing";
		@ConfigEntry(id = "energy_using_produce", type = EntryType.INTEGER, translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_produce")
		@Comment(value = "Energy usage per mb when produce Mekanism oxygen to player in water, rain", translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_produce.comment")
		public int energyUsingProduce = 200;
	}

	@ConfigObject
	public static class HotTemperatureProofModuleConfigObject
	{
		public static final String MODULES_HOT_TEMPERATURE_PROOF_PREFIX = MODULES_PREFIX + "space_fire_proof";
		@ConfigEntry(id = "energy_using_proof", type = EntryType.INTEGER, translation = MODULES_HOT_TEMPERATURE_PROOF_PREFIX + "_energy_using")
		@Comment(value = "Energy usage for proof in every ticks " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_HOT_TEMPERATURE_PROOF_PREFIX + "_energy_using.comment")
		public int energyUsingProof = 10 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
	}

	@ConfigObject
	public static class AcidRainProofModuleConfigObject
	{
		public static final String MODULES_ACID_RAIN_PROOF_PREFIX = MODULES_PREFIX + "acid_rain_proof";
		@ConfigEntry(id = "energy_using_proof", type = EntryType.INTEGER, translation = MODULES_ACID_RAIN_PROOF_PREFIX + "_energy_using")
		@Comment(value = "Energy usage for proof in every ticks " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_ACID_RAIN_PROOF_PREFIX + "_energy_using.comment")
		public int energyUsingProof = 10 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
	}

	@ConfigObject
	public static class GravityProofModuleConfigObject
	{
		public static final String MODULES_GRAVITY_PROOF_PREFIX = MODULES_PREFIX + "gravity_normalizing";
		@ConfigEntry(id = "energy_using_proof", type = EntryType.INTEGER, translation = MODULES_GRAVITY_PROOF_PREFIX + "_energy_using")
		@Comment(value = "Energy usage for proof in every ticks " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_GRAVITY_PROOF_PREFIX + "_energy_using.comment")
		public int energyUsingProof = 10 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
	}

}
