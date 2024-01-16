package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.resources.ResourceLocation;

public class SpaceFireProofUtils extends ProofAbstractUtils
{
	public static final SpaceFireProofEnchantmentFunction ENCHANTMENT_FUNCTION = new SpaceFireProofEnchantmentFunction();
	public static final SpaceFireProofUtils INSTANCE = new SpaceFireProofUtils(AdAstraGiselleAddon.rl("space_fire_proof"));

	private SpaceFireProofUtils(ResourceLocation id)
	{
		super(id);

		this.register(ENCHANTMENT_FUNCTION);
	}

}
