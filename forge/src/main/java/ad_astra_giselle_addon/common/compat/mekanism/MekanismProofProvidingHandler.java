package ad_astra_giselle_addon.common.compat.mekanism;

import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleAcidRainProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleGravityProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleHotTemperatureProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleOxygenProofUnit;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import mekanism.api.gear.IModule;
import net.minecraft.world.entity.LivingEntity;

public class MekanismProofProvidingHandler
{
	public int onOxygenProof(LivingEntity living)
	{
		IModule<ModuleOxygenProofUnit> module = AddonModuleHelper.findArmorEnabledModule(living, AddonMekanismModules.OXYGEN_PROOF_UNIT);
		return module != null && module.getCustomInstance().useResources(module, living, false) ? ProofAbstractUtils.OXYGEN_PROOF_INTERVAL : 0;
	}

	public int onHotTemperatureProof(LivingEntity living)
	{
		return AddonModuleHelper.useEnergy(living, AddonMekanismModules.HOT_TEMPERATURE_PROOF_UNIT, ModuleHotTemperatureProofUnit::getEnergyUsing) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
	}

	public int onAcidRainProof(LivingEntity living)
	{
		return AddonModuleHelper.useEnergy(living, AddonMekanismModules.ACID_RAIN_PROOF_UNIT, ModuleAcidRainProofUnit::getEnergyUsing) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
	}

	public int onGravityProof(LivingEntity living)
	{
		return AddonModuleHelper.useEnergy(living, AddonMekanismModules.GRAVITY_PROOF_UNIT, ModuleGravityProofUnit::getEnergyUsing) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
	}

	public MekanismProofProvidingHandler()
	{

	}

}
