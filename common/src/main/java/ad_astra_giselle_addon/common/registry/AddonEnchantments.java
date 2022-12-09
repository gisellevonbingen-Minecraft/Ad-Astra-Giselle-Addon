package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.enchantment.EnchantmentAcidRainProof;
import ad_astra_giselle_addon.common.enchantment.EnchantmentSpaceBreathing;
import ad_astra_giselle_addon.common.enchantment.EnchantmentSpaceFireProof;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;

public class AddonEnchantments
{
	public static final DelegateObjectCollection<Enchantment> ENCHANTMENTS = new DelegateObjectCollection<>(AdAstraGiselleAddon.MOD_ID, Registry.ENCHANTMENT_REGISTRY);
	public static final DelegateObjectHolder<EnchantmentSpaceBreathing> SPACE_BREATHING = ENCHANTMENTS.add("space_breathing", EnchantmentSpaceBreathing::new);
	public static final DelegateObjectHolder<EnchantmentSpaceFireProof> SPACE_FIRE_PROOF = ENCHANTMENTS.add("space_fire_proof", EnchantmentSpaceFireProof::new);
	public static final DelegateObjectHolder<EnchantmentAcidRainProof> ACID_RAIN_PROOF = ENCHANTMENTS.add("acid_rain_proof", EnchantmentAcidRainProof::new);

	private AddonEnchantments()
	{

	}

}
