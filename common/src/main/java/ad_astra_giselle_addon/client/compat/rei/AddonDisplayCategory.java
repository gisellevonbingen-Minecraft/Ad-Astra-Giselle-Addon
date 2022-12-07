package ad_astra_giselle_addon.client.compat.rei;

import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.client.compat.RecipeHelper;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public abstract class AddonDisplayCategory<R extends Display> implements DisplayCategory<R>
{
	private final CategoryIdentifier<R> identifier;

	public AddonDisplayCategory(CategoryIdentifier<R> identifier)
	{
		this.identifier = identifier;
	}

	@Override
	public Component getTitle()
	{
		return RecipeHelper.getCategoryTitle(this.getIdentifier());
	}

	@Override
	public CategoryIdentifier<? extends R> getCategoryIdentifier()
	{
		return this.identifier;
	}

	public List<ItemStack> getWorkStationItemStacks()
	{
		return new ArrayList<>();
	}

	public void registerRecipes(DisplayRegistry registry)
	{

	}

	public void addTransferHandler(TransferHandlerRegistry registry)
	{

	}

	public void registerGuiHandlers(ScreenRegistry registry)
	{

	}

}
