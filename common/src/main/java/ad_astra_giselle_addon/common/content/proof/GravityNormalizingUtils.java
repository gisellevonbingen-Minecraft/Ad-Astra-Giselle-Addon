package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.resources.ResourceLocation;

public class GravityNormalizingUtils extends ProofAbstractUtils
{
	public static final GravityNormalizingEnchantmentFunction ENCHANTMENT_FUNCTION = new GravityNormalizingEnchantmentFunction();
	public static final GravityNormalizingUtils INSTANCE = new GravityNormalizingUtils(AdAstraGiselleAddon.rl("gravity_noramlizing"));

	private GravityNormalizingUtils(ResourceLocation id)
	{
		super(id);

		this.register(ENCHANTMENT_FUNCTION);
	}

}
