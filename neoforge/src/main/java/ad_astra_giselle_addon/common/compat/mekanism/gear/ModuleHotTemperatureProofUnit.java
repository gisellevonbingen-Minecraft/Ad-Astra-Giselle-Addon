package ad_astra_giselle_addon.common.compat.mekanism.gear;

import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.math.FloatingLong;

public class ModuleHotTemperatureProofUnit implements ICustomModule<ModuleHotTemperatureProofUnit>
{
	private FloatingLong energyUsing;

	@Override
	public void init(IModule<ModuleHotTemperatureProofUnit> module, ModuleConfigItemCreator configItemCreator)
	{
		ICustomModule.super.init(module, configItemCreator);

		this.energyUsing = FloatingLong.create(AddonMekanismConfig.MODULES_HOT_TEMPERATURE_PROOF_ENERGY_USING);
	}

	public FloatingLong getEnergyUsing()
	{
		return this.energyUsing;
	}

}
