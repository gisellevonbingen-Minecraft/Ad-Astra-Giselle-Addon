package ad_astra_giselle_addon.common.compat.rei;

import ad_astra_giselle_addon.common.registry.AddonBlocks;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;

public class AddonReiCommonPlugin implements REIServerPlugin
{
	public static final CategoryIdentifier<FuelLoaderDisplay> FUEL_LOADER_CATEGORY = CategoryIdentifier.of(AddonBlocks.FUEL_LOADER.getId());
}
