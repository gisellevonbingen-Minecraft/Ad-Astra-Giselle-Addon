package ad_astra_giselle_addon.common.compat.mekanism;

import ad_astra_giselle_addon.common.compat.mekanism.AddonModuleHelper.ItemStackWithModule;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleAcidRainProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleGravityProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleHotTemperatureProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleOxygenProofUnit;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class MekanismProofProvidingHandler
{
	public int onOxygenProof(Entity entity)
	{
		// TODO: Support LivingEntity
		if (entity instanceof Player living)
		{
			ItemStackWithModule<ModuleOxygenProofUnit> tuple = AddonModuleHelper.findArmorEnabledModule(living, AddonMekanismModules.OXYGEN_PROOF_UNIT);
			return tuple != null && tuple.module().getCustomInstance().useResources(tuple.module(), living, false) ? ProofAbstractUtils.OXYGEN_PROOF_INTERVAL : 0;
		}
		else
		{
			return 0;
		}

	}

	public int onHotTemperatureProof(Entity entity)
	{
		// TODO: Support LivingEntity
		if (entity instanceof Player living)
		{
			return AddonModuleHelper.useEnergy(living, AddonMekanismModules.HOT_TEMPERATURE_PROOF_UNIT, ModuleHotTemperatureProofUnit::getEnergyUsingProof) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
		}
		else
		{
			return 0;
		}

	}

	public int onAcidRainProof(Entity entity)
	{
		// TODO: Support LivingEntity
		if (entity instanceof Player living)
		{
			return AddonModuleHelper.useEnergy(living, AddonMekanismModules.ACID_RAIN_PROOF_UNIT, ModuleAcidRainProofUnit::getEnergyUsingProof) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
		}
		else
		{
			return 0;
		}

	}

	public int onGravityProof(Entity entity)
	{
		// TODO: Support LivingEntity
		if (entity instanceof Player living)
		{
			return AddonModuleHelper.useEnergy(living, AddonMekanismModules.GRAVITY_PROOF_UNIT, ModuleGravityProofUnit::getEnergyUsingProof) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
		}
		else
		{
			return 0;
		}

	}

	public MekanismProofProvidingHandler()
	{

	}

}
