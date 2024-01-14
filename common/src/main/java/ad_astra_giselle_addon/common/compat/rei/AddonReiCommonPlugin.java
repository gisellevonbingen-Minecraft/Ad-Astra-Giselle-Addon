package ad_astra_giselle_addon.common.compat.rei;

import java.util.function.Function;

import ad_astra_giselle_addon.common.menu.AddonMachineMenu;
import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import earth.terrarium.adastra.common.compat.rei.categories.NasaWorkbenchCategory;
import earth.terrarium.adastra.common.compat.rei.displays.NasaWorkbenchDisplay;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoProvider;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleMenuInfoProvider;

@SuppressWarnings({"removal", "deprecation"})
public class AddonReiCommonPlugin implements REIServerPlugin
{
	public static final CategoryIdentifier<FuelLoaderDisplay> FUEL_LOADER_CATEGORY = CategoryIdentifier.of(AddonBlocks.FUEL_LOADER.getId());

	public AddonReiCommonPlugin()
	{

	}

	@Override
	public void registerMenuInfo(MenuInfoRegistry registry)
	{
		registry.register(NasaWorkbenchCategory.ID, AutomationNasaWorkbenchMenu.class, this.createMenuInfoProvider(display -> new AddonMachineMenuInfo<>(display, NasaWorkbenchDisplay::recipe)));
	}

	private <MENU extends AddonMachineMenu<?>, DISPLAY extends Display> MenuInfoProvider<MENU, DISPLAY> createMenuInfoProvider(Function<DISPLAY, AddonMachineMenuInfo<MENU, DISPLAY>> func)
	{
		return (SimpleMenuInfoProvider<MENU, DISPLAY>) func::apply;
	}

}
