package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;

public class SpaceOxygenProofEnchantmentFunction extends ProofEnchantmentFunction
{
	@Override
	public Enchantment getEnchantment()
	{
		return AddonEnchantments.SPACE_BREATHING.get();
	}

	@Override
	public boolean consume(LivingEntity living, ItemStackReference enchantedItem, ItemUsableResource resource, boolean simulate)
	{
		if (!super.consume(living, enchantedItem, resource, simulate))
		{
			return false;
		}
		else if (LivingHelper.isPlayingMode(living))
		{
			long oxygenUsing = this.getOxygenUsing(resource);
			IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractable(living, oxygenUsing);

			if (oxygenCharger == null)
			{
				return false;
			}

			FluidContainer fluidContainer = oxygenCharger.getFluidContainer();
			FluidHolder extracted = FluidUtils2.extractFluid(fluidContainer, FluidPredicates::isOxygen, oxygenUsing, simulate);
			return extracted.getFluidAmount() >= oxygenUsing;
		}
		else
		{
			return true;
		}

	}

	@Override
	public long getResourceUsingAmount(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return EnchantmentsConfig.SPACE_BREATHING_ENERGY_USING;
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return EnchantmentsConfig.SPACE_BREATHING_DURABILITY_USING;
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
			return ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return EnchantmentsConfig.SPACE_BREATHING_DURABILITY_DURATION;
		}
		else
		{
			return 0;
		}

	}

	public long getOxygenUsing(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return ProofAbstractUtils.OXYGEN_PROOF_USING;
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return EnchantmentsConfig.SPACE_BREATHING_DURABILITY_OXYGEN;
		}
		else
		{
			return 0;
		}

	}

}
