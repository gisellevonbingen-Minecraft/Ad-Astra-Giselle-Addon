package ad_astra_giselle_addon.common.compat.mekanism;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigOption.Separator;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.config.ForgeCompatsConfig;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;

@Category(value = AddonMekanismConfig.ID)
public final class AddonMekanismConfig
{
	public static final String ID = MekanismCompat.MOD_ID;
	public static final String PREFIX = ForgeCompatsConfig.PREFIX + "." + ID;
	public static final String MODULES_ID = "modules";

	public static final String MODULES_OXYGEN_PROOF_ID = MODULES_ID + "_space_breathing";
	public static final String MODULES_OXYGEN_PROOF_PREFIX = PREFIX + "." + MODULES_OXYGEN_PROOF_ID;
	@Separator(value = MODULES_OXYGEN_PROOF_ID, description = MODULES_OXYGEN_PROOF_PREFIX)
	@ConfigEntry(id = MODULES_OXYGEN_PROOF_ID + "_energy_using_provide", type = EntryType.INTEGER, translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_provide")
	@Comment(value = "Energy usage for provide oxygen " + EnchantmentsConfig.OXYGEN_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_provide.comment")
	public static int MODULES_OXYGEN_PROOF_ENERGY_USING_PROVIDE = 10 * ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;
	@ConfigEntry(id = MODULES_OXYGEN_PROOF_ID + "_energy_using_produce", type = EntryType.INTEGER, translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_produce")
	@Comment(value = "Energy usage per mb when produce Mekanism oxygen to player in water, rain", translation = MODULES_OXYGEN_PROOF_PREFIX + "_energy_using_produce.comment")
	public static int MODULES_OXYGEN_PROOF_ENERGY_USING_PRODUCE = 200;

	public static final String MODULES_HOT_TEMPERATURE_PROOF_ID = MODULES_ID + "_space_fire_proof";
	public static final String MODULES_HOT_TEMPERATURE_PROOF_PREFIX = PREFIX + "." + MODULES_HOT_TEMPERATURE_PROOF_ID;
	@Separator(value = MODULES_HOT_TEMPERATURE_PROOF_ID, description = MODULES_HOT_TEMPERATURE_PROOF_PREFIX)
	@ConfigEntry(id = MODULES_HOT_TEMPERATURE_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = MODULES_HOT_TEMPERATURE_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for proof in every ticks " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_HOT_TEMPERATURE_PROOF_PREFIX + "_energy_using.comment")
	public static int MODULES_HOT_TEMPERATURE_PROOF_ENERGY_USING = 10 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String MODULES_ACID_RAIN_PROOF_ID = MODULES_ID + "_acid_rain_proof";
	public static final String MODULES_ACID_RAIN_PROOF_PREFIX = PREFIX + "." + MODULES_ACID_RAIN_PROOF_ID;
	@Separator(value = MODULES_ACID_RAIN_PROOF_ID, description = MODULES_ACID_RAIN_PROOF_PREFIX)
	@ConfigEntry(id = MODULES_ACID_RAIN_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = MODULES_ACID_RAIN_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for proof in every ticks " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_ACID_RAIN_PROOF_PREFIX + "_energy_using.comment")
	public static int MODULES_ACID_RAIN_PROOF_ENERGY_USING = 10 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;

	public static final String MODULES_GRAVITY_PROOF_ID = MODULES_ID + "_gravity_normalizing";
	public static final String MODULES_GRAVITY_PROOF_PREFIX = PREFIX + "." + MODULES_GRAVITY_PROOF_ID;
	@Separator(value = MODULES_GRAVITY_PROOF_ID, description = MODULES_GRAVITY_PROOF_PREFIX)
	@ConfigEntry(id = MODULES_GRAVITY_PROOF_ID + "_energy_using", type = EntryType.INTEGER, translation = MODULES_GRAVITY_PROOF_PREFIX + "_energy_using")
	@Comment(value = "Energy usage for proof in every ticks " + EnchantmentsConfig.GENERAL_ENERGY_INTERVAL_TOOLTIP, translation = MODULES_GRAVITY_PROOF_PREFIX + "_energy_using.comment")
	public static int MODULES_GRAVITY_PROOF_ENERGY_USING = 10 * ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
}
