package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.resources.ResourceLocation;

public class SpaceOxygenProofUtils extends ProofAbstractUtils
{
	public static final SpaceOxygenProofEnchantmentFunction ENCHANTMENT_FUNCTION = new SpaceOxygenProofEnchantmentFunction();
	public static final SpaceOxygenProofUtils INSTANCE = new SpaceOxygenProofUtils(AdAstraGiselleAddon.rl("space_breathing"));

	private SpaceOxygenProofUtils(ResourceLocation id)
	{
		super(id);

		this.register(ENCHANTMENT_FUNCTION);
	}

}
