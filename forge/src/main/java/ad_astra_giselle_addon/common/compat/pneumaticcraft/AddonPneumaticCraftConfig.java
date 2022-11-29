package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

import ad_astra_giselle_addon.common.config.CompatsConfig;

@Category(id = AddonPneumaticCraftConfig.ID, translation = AddonPneumaticCraftConfig.PREFIX)
public final class AddonPneumaticCraftConfig
{
	public static final String ID = PneumaticCraftCompat.MODID;
	public static final String PREFIX = CompatsConfig.PREFIX + "." + ID;
	public static final String UPGRADE_ID = "upgades";

	public static final String SPACE_BREATHING_ID = UPGRADE_ID + ".space_breathing";
	public static final String SPACE_BREATHING_PREFIX = PREFIX + "." + SPACE_BREATHING_ID;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".oxygen_duration", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".oxygen_duration")
	@Comment(value = "Duration of provided oxygen (oxygen provide interval)", translation = SPACE_BREATHING_PREFIX + ".oxygen_duration.comment")
	public static int SPACE_BREATHING_OXYGEN_DURATION = 4;
	@ConfigEntry(id = SPACE_BREATHING_ID + ".air_using", type = EntryType.INTEGER, translation = SPACE_BREATHING_PREFIX + ".air_using")
	@Comment(value = "Air usage for provide oxygen in every ticks", translation = SPACE_BREATHING_PREFIX + ".air_using.comment")
	public static int SPACE_BREATHING_AIR_USING = 20;

	public static final String SPACE_FIRE_PROOF_ID = UPGRADE_ID + ".space_fire_proof";
	public static final String SPACE_FIRE_PROOF_PREFIX = PREFIX + "." + SPACE_FIRE_PROOF_ID;
	@ConfigEntry(id = SPACE_FIRE_PROOF_ID + ".air_using", type = EntryType.INTEGER, translation = SPACE_FIRE_PROOF_PREFIX + ".air_using")
	@Comment(value = "Air usage for proof in every ticks", translation = SPACE_FIRE_PROOF_PREFIX + ".air_using.comment")
	public static int SPACE_FIRE_PROOF_AIR_USING = 5;

	public static final String VENUS_ACID_PROOFID = UPGRADE_ID + ".venus_acid_proof";
	public static final String VENUS_ACID_PROOF_PREFIX = PREFIX + "." + VENUS_ACID_PROOFID;
	@ConfigEntry(id = VENUS_ACID_PROOFID + ".air_using", type = EntryType.INTEGER, translation = VENUS_ACID_PROOF_PREFIX + ".air_using")
	@Comment(value = "Air usage for proof in every ticks", translation = VENUS_ACID_PROOF_PREFIX + ".air_using.comment")
	public static int VENUS_ACID_PROOF_AIR_USING = 5;
}
