package ad_astra_giselle_addon.common.registries;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.enchantment.EnchantmentSpaceBreathing;
import ad_astra_giselle_addon.common.enchantment.EnchantmentSpaceFireProof;
import ad_astra_giselle_addon.common.enchantment.EnchantmentVenusAcidProof;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;

public class AddonEnchantments
{
	public static final DelegateObjectCollection<Enchantment> ENCHANTMENTS = new DelegateObjectCollection<>(AdAstraGiselleAddon.MOD_ID, Registry.ENCHANTMENT_REGISTRY);
	public static final DelegateObjectHolder<EnchantmentSpaceBreathing> SPACE_BREATHING = ENCHANTMENTS.register("space_breathing", EnchantmentSpaceBreathing::new);
	public static final DelegateObjectHolder<EnchantmentSpaceFireProof> SPACE_FIRE_PROOF = ENCHANTMENTS.register("space_fire_proof", EnchantmentSpaceFireProof::new);
	public static final DelegateObjectHolder<EnchantmentVenusAcidProof> VENUS_ACID_PROOF = ENCHANTMENTS.register("venus_acid_proof", EnchantmentVenusAcidProof::new);

	private AddonEnchantments()
	{

	}

}