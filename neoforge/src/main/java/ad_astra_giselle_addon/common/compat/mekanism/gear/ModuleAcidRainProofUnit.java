package ad_astra_giselle_addon.common.compat.mekanism.gear;

import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import mekanism.api.gear.ICustomModule;

public class ModuleAcidRainProofUnit implements ICustomModule<ModuleAcidRainProofUnit>
{
	private long energyUsingProof;

	public ModuleAcidRainProofUnit()
	{
		this.energyUsingProof = AddonMekanismConfig.MODULES_ACID_RAIN_PROOF.energyUsingProof;
	}

	public long getEnergyUsingProof()
	{
		return this.energyUsingProof;
	}

}
