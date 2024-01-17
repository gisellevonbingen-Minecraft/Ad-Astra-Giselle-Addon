package ad_astra_giselle_addon.common.compat.mekanism;

import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleGravityNormalizingUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleSpaceBreathingUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleSpaceFireProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleVenusAcidProofUnit;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import mekanism.api.gear.IModule;
import net.minecraft.world.entity.LivingEntity;

public class MekanismProofProvidingHandler
{
	public int onLivingSpaceOxygenProof(LivingEntity living)
	{
		IModule<ModuleSpaceBreathingUnit> module = AddonModuleHelper.findArmorEnabledModule(living, AddonMekanismModules.SPACE_BREATHING_UNIT);
		return module != null && module.getCustomInstance().useResources(module, living, false) ? ProofAbstractUtils.OXYGEN_PROOF_INTERVAL : 0;
	}

	public int onLivingSpaceFireProof(LivingEntity living)
	{
		return AddonModuleHelper.useEnergy(living, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT, ModuleSpaceFireProofUnit::getEnergyUsing) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
	}

	public int onLivingVenusAcidProof(LivingEntity living)
	{
		return AddonModuleHelper.useEnergy(living, AddonMekanismModules.ACID_RAIN_PROOF_UNIT, ModuleVenusAcidProofUnit::getEnergyUsing) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
	}

	public int onLivingGravityNormalizing(LivingEntity living)
	{
		return AddonModuleHelper.useEnergy(living, AddonMekanismModules.GRAVITY_NORMALIZING_UNIT, ModuleGravityNormalizingUnit::getEnergyUsing) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
	}

	public MekanismProofProvidingHandler()
	{

	}

}
