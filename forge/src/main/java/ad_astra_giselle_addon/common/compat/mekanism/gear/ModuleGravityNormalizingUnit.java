package ad_astra_giselle_addon.common.compat.mekanism.gear;

import ad_astra_giselle_addon.common.config.AddonConfigs;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.math.FloatingLong;

public class ModuleGravityNormalizingUnit implements ICustomModule<ModuleGravityNormalizingUnit>
{
	private FloatingLong energyUsing;

	@Override
	public void init(IModule<ModuleGravityNormalizingUnit> module, ModuleConfigItemCreator configItemCreator)
	{
		ICustomModule.super.init(module, configItemCreator);

		this.energyUsing = FloatingLong.create(AddonConfigs.Common.mekanism.moduleGravityNormalizing_energyUsing.get());
	}

	public FloatingLong getEnergyUsing()
	{
		return this.energyUsing;
	}

}
