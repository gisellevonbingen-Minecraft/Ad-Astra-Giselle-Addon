package ad_astra_giselle_addon.common.compat.mekanism;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

import ad_astra_giselle_addon.common.config.CompatsConfig;

@Category(id = AddonMekanismConfig.ID, translation = AddonMekanismConfig.PREFIX)
public final class AddonMekanismConfig
{
	public static final String ID = MekanismCompat.MODID;
	public static final String PREFIX = CompatsConfig.PREFIX + "." + ID;
	public static final String MODULES_ID = "modules";
	public static final String MODULES_PREFIX = PREFIX + "." + MODULES_ID;

	public static final String MODULES_SPACE_BREATHING_ID = "space_breathing";
	public static final String MODULES_SPACE_BREATHING_PREFIX = MODULES_PREFIX + "." + MODULES_SPACE_BREATHING_ID;
	@ConfigEntry(id = MODULES_SPACE_BREATHING_ID + ".oxygen_duration", type = EntryType.INTEGER, translation = MODULES_SPACE_BREATHING_PREFIX + ".oxygen_duration")
	public static int MODULES_SPACE_BREATHING_OXYGEN_DURATION = 4;
	@ConfigEntry(id = MODULES_SPACE_BREATHING_ID + ".energy_using_provide", type = EntryType.INTEGER, translation = MODULES_SPACE_BREATHING_PREFIX + ".energy_using_provide")
	public static int MODULES_SPACE_BREATHING_ENERGY_USING_PROVIDE = 40;
	@ConfigEntry(id = MODULES_SPACE_BREATHING_ID + ".energy_using_produce", type = EntryType.INTEGER, translation = MODULES_SPACE_BREATHING_PREFIX + ".energy_using_produce")
	public static int MODULES_SPACE_BREATHING_ENERGY_USING_PRODUCE = 200;

	public static final String MODULES_SPACE_FIRE_PROOF_ID = "space_fire_proof";
	public static final String SPACE_FIRE_MODULES_PROOF_PREFIX = MODULES_PREFIX + "." + MODULES_SPACE_FIRE_PROOF_ID;
	@ConfigEntry(id = MODULES_SPACE_FIRE_PROOF_ID + ".energy_using", type = EntryType.INTEGER, translation = SPACE_FIRE_MODULES_PROOF_PREFIX + ".energy_using")
	public static int MODULES_SPACE_FIRE_PROOF_ENERGY_USING = 10;

	public static final String MODULES_VENUS_ACID_PROOF_ID = "venus_acid_proof";
	public static final String MODULES_VENUS_ACID_PROOF_PREFIX = MODULES_PREFIX + "." + MODULES_VENUS_ACID_PROOF_ID;
	@ConfigEntry(id = MODULES_VENUS_ACID_PROOF_ID + ".energy_using", type = EntryType.INTEGER, translation = MODULES_VENUS_ACID_PROOF_PREFIX + ".energy_using")
	public static int MODULES_VENUS_ACID_PROOF_ENERGY_USING = 10;
}
