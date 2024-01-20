package ad_astra_giselle_addon.common.compat.mekanism;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigSeparator;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.config.ForgeCompatsConfig;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;

@Category(id = AddonMekanismConfig.ID, translation = AddonMekanismConfig.PREFIX)
public final class AddonMekanismConfig
{
	public static final String ID = MekanismCompat.MOD_ID;
	public static final String PREFIX = ForgeCompatsConfig.PREFIX + "." + ID;
	public static final String MODULES_ID = "modules";

	public static final String MODULES_OXYGEN_PROOF_ID = MODULES_ID + "_space_breathing";
	public static final String MODULES_OXYGEN_PROOF_PREFIX = PREFIX + "." + MODULES_OXYGEN_PROOF_ID;
	@ConfigSeparator(translation = MODULES_OXYGEN_PROOF_PREFIX)
	@ConfigEntry(id = MODULES_OXYGEN_PROOF_ID + "_energy_using_provide", type = EntryType.INTEGER, translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_provide")
	@Comment(value = "Energy usage for provide oxygen " + EnchantmentsConfig.OXYGEN_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_provide.comment")
	public static int MODULES_OXYGEN_PROOF_ENERGY_USING_PROVIDE = 10 * ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;
	@ConfigEntry(id = MODULES_OXYGEN_PROOF_ID + "_energy_using_produce", type = EntryType.INTEGER, translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_produce")
	@Comment(value = "Energy usage per mb when produce Mekanism oxygen to player in water, rain", translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_produce.comment")
	public static int MODULES_OXYGEN_PROOF_ENERGY_USING_PRODUCE = 200;

	public static final String MODULES_HOT_TEMPERATURE_PROOF_ID = MODULES_ID + "_space_fire_proof";
	public static final String HOT_TEMPERATURE_MODULES_PROOF_PREFIX = PREFIX + "." + MODULES_HOT_TEMPERATURE_PROOF_ID;
	@ConfigSeparator(translation = HOT_TEMPERATURE_MODULES_PROOF_PREFIX)
	@ConfigEntry(id = MODULES_HOT_TEMPERATURE_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = HOT_TEMPERATURE_MODULES_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for proof in every ticks " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = HOT_TEMPERATURE_MODULES_PROOF_PREFIX + "_energy_using.comment")
	public static int MODULES_HOT_TEMPERATURE_PROOF_ENERGY_USING = 10 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String MODULES_ACID_RAIN_PROOF_ID = MODULES_ID + "_acid_rain_proof";
	public static final String MODULES_ACID_RAIN_PROOF_PREFIX = PREFIX + "." + MODULES_ACID_RAIN_PROOF_ID;
	@ConfigSeparator(translation = MODULES_ACID_RAIN_PROOF_PREFIX)
	@ConfigEntry(id = MODULES_ACID_RAIN_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = MODULES_ACID_RAIN_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for proof in every ticks " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_ACID_RAIN_PROOF_PREFIX + "_energy_using.comment")
	public static int MODULES_ACID_RAIN_PROOF_ENERGY_USING = 10 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String MODULES_GRAVITY_PROOF_ID = MODULES_ID + "_gravity_normalizing";
	public static final String MODULES_GRAVITY_PROOF_PREFIX = PREFIX + "." + MODULES_GRAVITY_PROOF_ID;
	@ConfigSeparator(translation = MODULES_GRAVITY_PROOF_PREFIX)
	@ConfigEntry(id = MODULES_GRAVITY_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = MODULES_GRAVITY_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for proof in every ticks " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_GRAVITY_PROOF_PREFIX + "_energy_using.comment")
	public static int MODULES_GRAVITY_PROOF_ENERGY_USING = 10 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
}
