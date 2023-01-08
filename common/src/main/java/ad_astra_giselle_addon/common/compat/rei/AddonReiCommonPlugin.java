package ad_astra_giselle_addon.common.compat.rei;

import java.util.function.Function;

import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import earth.terrarium.ad_astra.common.compat.rei.MachineMenuInfo;
import earth.terrarium.ad_astra.common.compat.rei.REICategories;
import earth.terrarium.ad_astra.common.compat.rei.nasa_workbench.NasaWorkbenchDisplay;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoProvider;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleMenuInfoProvider;

public class AddonReiCommonPlugin implements REIServerPlugin
{
	public static final CategoryIdentifier<FuelLoaderDisplay> FUEL_LOADER_CATEGORY = CategoryIdentifier.of(AddonBlocks.FUEL_LOADER.getId());

	@Override
	public void registerMenuInfo(MenuInfoRegistry registry)
	{
		registry.register(REICategories.NASA_WORKBENCH_CATEGORY, AutomationNasaWorkbenchMenu.class, this.createMenuInfoProvider(display -> new MachineMenuInfo<>(display, NasaWorkbenchDisplay::recipe)
		{
			@Override
			protected int getInputSlotCount(AutomationNasaWorkbenchMenu menu)
			{
				return 14;
			}
		}));
	}

	private <MENU extends AbstractMachineMenu<?>, DISPLAY extends Display> MenuInfoProvider<MENU, DISPLAY> createMenuInfoProvider(Function<DISPLAY, MachineMenuInfo<MENU, DISPLAY>> func)
	{
		return (SimpleMenuInfoProvider<MENU, DISPLAY>) func::apply;
	}
}
