package ad_astra_giselle_addon.client.compat.rei;

import java.util.Collections;
import java.util.List;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.level.material.Fluid;

public record FuelLoaderDisplay(Fluid fluid) implements Display
{
	@Override
	public List<EntryIngredient> getInputEntries()
	{
		return Collections.singletonList(EntryIngredients.of(this.fluid()));
	}

	@Override
	public List<EntryIngredient> getOutputEntries()
	{
		return Collections.emptyList();
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier()
	{
		return AddonReiPlugin.FUEL_LOADER_CATEGORY;
	}

}
