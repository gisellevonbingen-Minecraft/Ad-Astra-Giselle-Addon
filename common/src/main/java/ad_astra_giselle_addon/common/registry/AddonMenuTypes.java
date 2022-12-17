package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;

public class AddonMenuTypes
{
	public static final MenuTypeRegistryCollection MENU_TYPES = new MenuTypeRegistryCollection(AdAstraGiselleAddon.MOD_ID);
	public static final MenuTypeRegistryHolder<FuelLoaderMenu> FUEL_LOADER = MENU_TYPES.add("fuel_loader", FuelLoaderMenu::new);
	public static final MenuTypeRegistryHolder<AutomationNasaWorkbenchMenu> AUTOMATION_NASA_WORKBENCH = MENU_TYPES.add("automation_nasa_workbench", AutomationNasaWorkbenchMenu::new);

	private AddonMenuTypes()
	{

	}

}
