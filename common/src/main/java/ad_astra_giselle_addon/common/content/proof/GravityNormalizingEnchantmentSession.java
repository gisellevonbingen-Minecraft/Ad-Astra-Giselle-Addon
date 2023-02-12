package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.enchantment.AddonEnchantment;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import net.minecraft.world.entity.LivingEntity;

public class GravityNormalizingEnchantmentSession extends ProofEnchantmentSession
{
	public GravityNormalizingEnchantmentSession(LivingEntity living, AddonEnchantment enchantment)
	{
		super(living, enchantment);
	}

	@Override
	public long getResourceUsingAmount(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return EnchantmentsConfig.GRAVITY_NORMALIZING_ENERGY_USING;
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return EnchantmentsConfig.GRAVITY_NORMALIZING_DURABILITY_USING;
		}
		else
		{
			return 0;
		}

	}

	@Override
	public int getProofDuration(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return EnchantmentsConfig.GRAVITY_NORMALIZING_DURABILITY_DURATION;
		}
		else
		{
			return 0;
		}

	}

}
