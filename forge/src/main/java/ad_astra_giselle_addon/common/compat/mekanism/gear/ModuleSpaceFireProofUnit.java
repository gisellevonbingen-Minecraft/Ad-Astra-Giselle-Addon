package ad_astra_giselle_addon.common.compat.mekanism.gear;

import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.math.FloatingLong;

public class ModuleSpaceFireProofUnit implements ICustomModule<ModuleSpaceFireProofUnit>
{
	private FloatingLong energyUsing;

	@Override
	public void init(IModule<ModuleSpaceFireProofUnit> module, ModuleConfigItemCreator configItemCreator)
	{
		ICustomModule.super.init(module, configItemCreator);

		this.energyUsing = FloatingLong.create(AddonMekanismConfig.MODULES_SPACE_FIRE_PROOF_ENERGY_USING);
	}

	public FloatingLong getEnergyUsing()
	{
		return this.energyUsing;
	}

}
