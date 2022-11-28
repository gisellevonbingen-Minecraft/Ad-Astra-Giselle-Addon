package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.config.AddonConfigs;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.enchantment.EnchantmentEnergyStorageOrDamageable;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import ad_astra_giselle_addon.common.util.LivingEntityHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

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

		LivingEntity entity = this.getEntity();

		if (LivingEntityHelper.isPlayingMode(entity))
		{
			int oxygenUsing = this.getOxygenUsing();
			ItemStack enchantedItem = this.getEnchantedItem();
			IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractable(entity, oxygenUsing, enchantedItem);

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

		LivingEntity entity = this.getEntity();

		if (LivingEntityHelper.isPlayingMode(entity))
		{
			IOxygenCharger oxygenCharger = this.testedOxygenCharger;

			if (oxygenCharger != null && !entity.getLevel().isClientSide())
			{
				UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();
				int oxygenUsing = this.getOxygenUsing();
				FluidHooks2.extractFluid(fluidHandler, FluidPredicates::isOxygen, oxygenUsing, false);
			}

		}

	}

	@Override
	public int getResourceUsingAmount(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return AddonConfigs.Common.enchantments.space_breathing_energy_using.get();
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return AddonConfigs.Common.enchantments.space_breathing_durabiltiy_using.get();
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
			return AddonConfigs.Common.enchantments.space_breathing_energy_duration.get();
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return AddonConfigs.Common.enchantments.space_breathing_durability_duration.get();
		}
		else
		{
			return 0;
		}

	}

	public int getOxygenUsing()
	{
		ItemUsableResource resource = this.getTestedUsableResource();
		return this.getOxygenUsing(resource);
	}

	public int getOxygenUsing(ItemUsableResource resource)
	{
		if (resource == ItemUsableResource.Energy)
		{
			return AddonConfigs.Common.enchantments.space_breathing_energy_oxygen.get();
		}
		else if (resource == ItemUsableResource.Durability)
		{
			return AddonConfigs.Common.enchantments.space_breathing_durability_oxygen.get();
		}
		else
		{
			return 0;
		}

	}

}
