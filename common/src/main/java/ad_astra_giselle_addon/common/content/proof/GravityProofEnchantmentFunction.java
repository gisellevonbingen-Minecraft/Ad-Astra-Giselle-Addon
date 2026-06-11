package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class GravityProofEnchantmentFunction extends ProofEnchantmentFunction
{
	@Override
	public ResourceKey<Enchantment> getEnchantment()
	{
		return AddonEnchantments.GRAVITY_PROOF;
	}

	@Override
	public long getResourceUsingAmount(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return EnchantmentsConfig.GRAVITY_PROOF.energyUsing;
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return EnchantmentsConfig.GRAVITY_PROOF.durabilityUsing;
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
			return EnchantmentsConfig.GRAVITY_PROOF.durabilityDuration;
		}
		else
		{
			return 0;
		}

	}

}
