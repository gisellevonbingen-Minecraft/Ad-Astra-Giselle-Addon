package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.enchantment.AddonEnchantment;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import net.minecraft.world.entity.LivingEntity;

public class SpaceFireProofEnchantmentSession extends ProofEnchantmentSession
{
	public SpaceFireProofEnchantmentSession(LivingEntity living, AddonEnchantment enchantment)
	{
		super(living, enchantment);
	}

	@Override
	public long getResourceUsingAmount(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return EnchantmentsConfig.SPACE_FIRE_PROOF_ENERGY_USING;
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return EnchantmentsConfig.SPACE_FIRE_PROOF_DURABILITY_USING;
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
			return EnchantmentsConfig.SPACE_FIRE_PROOF_DURABILITY_DURATION;
		}
		else
		{
			return 0;
		}

	}

}
