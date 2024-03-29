package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;
import ad_astra_giselle_addon.common.menu.GravityNormalizerMenu;
import ad_astra_giselle_addon.common.menu.RocketSensorMenu;

public class AddonMenuTypes
{
	public static final MenuTypeRegistryCollection MENU_TYPES = new MenuTypeRegistryCollection(AdAstraGiselleAddon.MOD_ID);
	public static final MenuTypeRegistryHolder<FuelLoaderMenu> FUEL_LOADER = MENU_TYPES.add("fuel_loader", FuelLoaderMenu::new);
	public static final MenuTypeRegistryHolder<AutomationNasaWorkbenchMenu> AUTOMATION_NASA_WORKBENCH = MENU_TYPES.add("automation_nasa_workbench", AutomationNasaWorkbenchMenu::new);
	public static final MenuTypeRegistryHolder<GravityNormalizerMenu> GRAVITY_NORMALIZER = MENU_TYPES.add("gravity_normalizer", GravityNormalizerMenu::new);
	public static final MenuTypeRegistryHolder<RocketSensorMenu> ROCKET_SENSOR = MENU_TYPES.add("rocket_sensor", RocketSensorMenu::new);

	private AddonMenuTypes()
	{

	}

}
