package ad_astra_giselle_addon.common.compat.mekanism;

import com.google.common.eventbus.Subscribe;

import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleSpaceBreathingUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleSpaceFireProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleVenusAcidProofUnit;
import ad_astra_giselle_addon.common.content.proof.LivingSpaceFireProofProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.LivingSpaceOxygenProofProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.LivingVenusAcidProofProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.content.proof.ProofSession;
import mekanism.common.content.gear.Module;

public class MekanismProofProvidingHandler
{
	@Subscribe
	public void onLivingSpaceOxygenProof(LivingSpaceOxygenProofProvidingEvent e)
	{
		e.add(living ->
		{
			Module<ModuleSpaceBreathingUnit> module = AddonModuleHelper.findArmorEnabledModule(living, AddonMekanismModules.SPACE_BREATHING_UNIT);

			return new ProofSession(living)
			{
				@Override
				public boolean canProvide()
				{
					return module != null && module.getCustomInstance().useResources(module, this.getLiving(), false);
				}

				@Override
				public int getProofDuration()
				{
					return module.getCustomInstance().getOxygenDuration();
				}
			};

		});

	}

	@Subscribe
	public void onLivingSpaceFireProof(LivingSpaceFireProofProvidingEvent e)
	{
		e.add(living -> new ProofSession(living)
		{
			@Override
			public boolean canProvide()
			{
				return AddonModuleHelper.useEnergy(this.getLiving(), AddonMekanismModules.SPACE_FIRE_PROOF_UNIT, ModuleSpaceFireProofUnit::getEnergyUsing);
			}

			@Override
			public int getProofDuration()
			{
				return ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
			}
		});

	}

	@Subscribe
	public void onLivingVenusAcidProof(LivingVenusAcidProofProvidingEvent e)
	{
		e.add(living -> new ProofSession(living)
		{
			@Override
			public boolean canProvide()
			{
				return AddonModuleHelper.useEnergy(this.getLiving(), AddonMekanismModules.ACID_RAIN_PROOF_UNIT, ModuleVenusAcidProofUnit::getEnergyUsing);
			}

			@Override
			public int getProofDuration()
			{
				return ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
			}
		});

	}

	public MekanismProofProvidingHandler()
	{

	}

}
