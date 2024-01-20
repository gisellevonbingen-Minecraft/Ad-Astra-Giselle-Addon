package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.enchantment.EnchantmentAcidRainProof;
import ad_astra_giselle_addon.common.enchantment.EnchantmentGravityProof;
import ad_astra_giselle_addon.common.enchantment.EnchantmentOxygenProof;
import ad_astra_giselle_addon.common.enchantment.EnchantmentHotTemperatureProof;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;

public class AddonEnchantments
{
	public static final ObjectRegistryCollection<Enchantment> ENCHANTMENTS = new ObjectRegistryCollection<>(AdAstraGiselleAddon.MOD_ID, Registry.ENCHANTMENT_REGISTRY);
	public static final ObjectRegistryHolder<EnchantmentOxygenProof> OXYGEN_PROOF = ENCHANTMENTS.add("space_breathing", EnchantmentOxygenProof::new);
	public static final ObjectRegistryHolder<EnchantmentHotTemperatureProof> HOT_TEMPERATURE_PROOF = ENCHANTMENTS.add("space_fire_proof", EnchantmentHotTemperatureProof::new);
	public static final ObjectRegistryHolder<EnchantmentAcidRainProof> ACID_RAIN_PROOF = ENCHANTMENTS.add("acid_rain_proof", EnchantmentAcidRainProof::new);
	public static final ObjectRegistryHolder<EnchantmentGravityProof> GRAVITY_PROOF = ENCHANTMENTS.add("gravity_normalizing", EnchantmentGravityProof::new);

	private AddonEnchantments()
	{

	}

}
