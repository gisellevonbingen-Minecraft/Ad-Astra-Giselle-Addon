package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.content.proof.AcidRainProofEnchantmentFunction;
import ad_astra_giselle_addon.common.content.proof.AcidRainProofUtils;
import ad_astra_giselle_addon.common.content.proof.GravityProofEnchantmentFunction;
import ad_astra_giselle_addon.common.content.proof.GravityProofUtils;
import ad_astra_giselle_addon.common.content.proof.HotTemperatureProofEnchantmentFunction;
import ad_astra_giselle_addon.common.content.proof.HotTemperatureProofUtils;
import ad_astra_giselle_addon.common.content.proof.OxygenProofEnchantmentFunction;
import ad_astra_giselle_addon.common.content.proof.OxygenProofUtils;

public class AddonProofs
{
	public static final OxygenProofUtils OXYGEN = new OxygenProofUtils(AdAstraGiselleAddon.rl("oxygen"));
	public static final HotTemperatureProofUtils HOT_TEMPERATURE = new HotTemperatureProofUtils(AdAstraGiselleAddon.rl("hot_temperature"));
	public static final AcidRainProofUtils ACID_RAIN = new AcidRainProofUtils(AdAstraGiselleAddon.rl("acid_rain"));
	public static final GravityProofUtils GRAVITY = new GravityProofUtils(AdAstraGiselleAddon.rl("gravity"));

	private AddonProofs()
	{

	}

	public static void registerAll()
	{
		OXYGEN.register(new OxygenProofEnchantmentFunction());
		HOT_TEMPERATURE.register(new HotTemperatureProofEnchantmentFunction());
		ACID_RAIN.register(new AcidRainProofEnchantmentFunction());
		GRAVITY.register(new GravityProofEnchantmentFunction());
	}

}
