package ad_astra_giselle_addon.common.registries;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;

public class AddonMenuTypes
{
	public static final MenuTypeDeferredRegister MENU_TYPES = new MenuTypeDeferredRegister(AdAstraGiselleAddon.MOD_ID);
	public static final RegistryObject<MenuType<FuelLoaderMenu>> FUEL_LOADER = MENU_TYPES.register("fuel_loader", FuelLoaderMenu::new);

	private AddonMenuTypes()
	{

	}

}
