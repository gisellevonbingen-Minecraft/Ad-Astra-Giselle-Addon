package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.resources.ResourceLocation;

public class AcidRainProofUtils extends ProofAbstractUtils
{
	public static final AcidRainProofEnchantmentFunction ENCHANTMENT_FUNCTION = new AcidRainProofEnchantmentFunction();
	public static final AcidRainProofUtils INSTANCE = new AcidRainProofUtils(AdAstraGiselleAddon.rl("acid_rain_proof"));

	private AcidRainProofUtils(ResourceLocation id)
	{
		super(id);

		this.register(ENCHANTMENT_FUNCTION);
	}

}
