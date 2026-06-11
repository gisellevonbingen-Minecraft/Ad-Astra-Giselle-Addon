package ad_astra_giselle_addon.common.compat.mekanism.gear;

import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import mekanism.api.gear.ICustomModule;

public class ModuleGravityProofUnit implements ICustomModule<ModuleGravityProofUnit>
{
	private long energyUsingProof;

	public ModuleGravityProofUnit()
	{
		this.energyUsingProof = AddonMekanismConfig.MODULES_GRAVITY_PROOF.energyUsingProof;
	}

	public long getEnergyUsingProof()
	{
		return this.energyUsingProof;
	}

}
