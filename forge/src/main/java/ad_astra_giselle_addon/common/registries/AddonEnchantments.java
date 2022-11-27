package ad_astra_giselle_addon.common.registries;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.enchantment.EnchantmentGravityNormalizing;
import ad_astra_giselle_addon.common.enchantment.EnchantmentSpaceBreathing;
import ad_astra_giselle_addon.common.enchantment.EnchantmentSpaceFireProof;
import ad_astra_giselle_addon.common.enchantment.EnchantmentVenusAcidProof;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AddonEnchantments
{
	public static final DeferredRegisterWrapper<Enchantment> ENCHANTMENTS = DeferredRegisterWrapper.create(AdAstraGiselleAddon.MOD_ID, ForgeRegistries.ENCHANTMENTS);
	public static final RegistryObject<EnchantmentSpaceBreathing> SPACE_BREATHING = ENCHANTMENTS.register("space_breathing", EnchantmentSpaceBreathing::new);
	public static final RegistryObject<EnchantmentGravityNormalizing> GRAVITY_NORMALIZING = ENCHANTMENTS.register("gravity_normalizing", EnchantmentGravityNormalizing::new);
	public static final RegistryObject<EnchantmentSpaceFireProof> SPACE_FIRE_PROOF = ENCHANTMENTS.register("space_fire_proof", EnchantmentSpaceFireProof::new);
	public static final RegistryObject<EnchantmentVenusAcidProof> VENUS_ACID_PROOF = ENCHANTMENTS.register("venus_acid_proof", EnchantmentVenusAcidProof::new);

	private AddonEnchantments()
	{

	}

}
