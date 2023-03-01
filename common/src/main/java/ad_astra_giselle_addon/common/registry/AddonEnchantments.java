package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.enchantment.EnchantmentAcidRainProof;
import ad_astra_giselle_addon.common.enchantment.EnchantmentGravityNormalizing;
import ad_astra_giselle_addon.common.enchantment.EnchantmentSpaceBreathing;
import ad_astra_giselle_addon.common.enchantment.EnchantmentSpaceFireProof;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;

public class AddonEnchantments
{
	public static final ObjectRegistryCollection<Enchantment> ENCHANTMENTS = new ObjectRegistryCollection<>(AdAstraGiselleAddon.MOD_ID, Registries.ENCHANTMENT);
	public static final ObjectRegistryHolder<EnchantmentSpaceBreathing> SPACE_BREATHING = ENCHANTMENTS.add("space_breathing", EnchantmentSpaceBreathing::new);
	public static final ObjectRegistryHolder<EnchantmentSpaceFireProof> SPACE_FIRE_PROOF = ENCHANTMENTS.add("space_fire_proof", EnchantmentSpaceFireProof::new);
	public static final ObjectRegistryHolder<EnchantmentAcidRainProof> ACID_RAIN_PROOF = ENCHANTMENTS.add("acid_rain_proof", EnchantmentAcidRainProof::new);
	public static final ObjectRegistryHolder<EnchantmentGravityNormalizing> GRAVITY_NORMALIZING = ENCHANTMENTS.add("gravity_normalizing", EnchantmentGravityNormalizing::new);

	private AddonEnchantments()
	{

	}

}
