package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.enchantment.EnchantmentEnergyStorageOrDamageable;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import ad_astra_giselle_addon.common.util.LivingHelper;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.entity.LivingEntity;

public class SpaceOxygenProofEnchantmentSession extends ProofEnchantmentSession
{
	private IOxygenCharger testedOxygenCharger;

	public SpaceOxygenProofEnchantmentSession(LivingEntity entity, EnchantmentEnergyStorageOrDamageable enchantment)
	{
		super(entity, enchantment);
	}

	@Override
	public boolean canProvide()
	{
		if (!super.canProvide())
		{
			return false;
		}

		LivingEntity entity = this.getLiving();

		if (LivingHelper.isPlayingMode(entity))
		{
			long oxygenUsing = this.getOxygenUsing();
			ItemStackHolder enchantedItem = this.getEnchantedItem();
			IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractable(entity, oxygenUsing, enchantedItem.getStack());

			if (oxygenCharger == null)
			{
				return false;
			}

			UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();

			if (FluidHooks2.extractFluid(fluidHandler, FluidPredicates::isOxygen, oxygenUsing, true).getFluidAmount() < oxygenUsing)
			{
				return false;
			}

			this.testedOxygenCharger = oxygenCharger;
		}

		return true;
	}

	@Override
	public void onProvide()
	{
		super.onProvide();

		LivingEntity entity = this.getLiving();

		if (LivingHelper.isPlayingMode(entity))
		{
			IOxygenCharger oxygenCharger = this.testedOxygenCharger;

			if (oxygenCharger != null && !entity.getLevel().isClientSide())
			{
				UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();
				long oxygenUsing = this.getOxygenUsing();
				FluidHooks2.extractFluid(fluidHandler, FluidPredicates::isOxygen, oxygenUsing, false);
			}

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

	public long getOxygenUsing()
	{
		ItemUsableResource resource = this.getTestedUsableResource();
		return this.getOxygenUsing(resource);
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
