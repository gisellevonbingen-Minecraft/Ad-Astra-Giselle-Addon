package ad_astra_giselle_addon.common.compat.mekanism.gear;

import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import mekanism.api.gear.ICustomModule;

public class ModuleHotTemperatureProofUnit implements ICustomModule<ModuleHotTemperatureProofUnit>
{
	private long energyUsingProof;

	public ModuleHotTemperatureProofUnit()
	{
		this.energyUsingProof = AddonMekanismConfig.MODULES_HOT_TEMPERATURE_PROOF.energyUsingProof;
	}

	public long getEnergyUsingProof()
	{
		return this.energyUsingProof;
	}

}
