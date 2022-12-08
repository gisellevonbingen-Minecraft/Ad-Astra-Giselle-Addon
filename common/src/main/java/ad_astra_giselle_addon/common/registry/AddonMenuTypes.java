package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;
import net.minecraft.world.inventory.MenuType;

public class AddonMenuTypes
{
	public static final DelegateMenuTypeCollection MENU_TYPES = new DelegateMenuTypeCollection(AdAstraGiselleAddon.MOD_ID);
	public static final DelegateObjectHolder<MenuType<FuelLoaderMenu>> FUEL_LOADER = MENU_TYPES.register("fuel_loader", FuelLoaderMenu::new);

	private AddonMenuTypes()
	{

	}

}
