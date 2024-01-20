package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;

public class HotTemperatureProofEnchantmentFunction extends ProofEnchantmentFunction
{
	@Override
	public Enchantment getEnchantment()
	{
		return AddonEnchantments.HOT_TEMPERATURE_PROOF.get();
	}

	@Override
	public long getResourceUsingAmount(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return EnchantmentsConfig.HOT_TEMPERATURE_PROOF_ENERGY_USING;
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return EnchantmentsConfig.HOT_TEMPERATURE_PROOF_DURABILITY_USING;
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
			return EnchantmentsConfig.HOT_TEMPERATURE_PROOF_DURABILITY_DURATION;
		}
		else
		{
			return 0;
		}

	}

}
