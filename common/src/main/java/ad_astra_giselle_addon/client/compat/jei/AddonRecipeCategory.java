package ad_astra_giselle_addon.client.compat.jei;

import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.client.compat.RecipeHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public abstract class AddonRecipeCategory<R> implements IRecipeCategory<R>
{
	private final RecipeType<R> recipeType;

	public AddonRecipeCategory(RecipeType<R> recipeType)
	{
		this.recipeType = recipeType;
	}

	@Override
	public abstract IDrawable getBackground();

	@Override
	public RecipeType<R> getRecipeType()
	{
		return this.recipeType;
	}

	@Override
	public Component getTitle()
	{
		return RecipeHelper.getCategoryTitle(this.getRecipeType().getUid());
	}

	@Override
	public IDrawable getIcon()
	{
		return null;
	}

	public void createGui(IGuiHelper guiHelper)
	{

	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses)
	{

	}

	public void registerRecipes(IRecipeRegistration registry)
	{

	}

	public void addTransferHandler(IRecipeTransferRegistration registry)
	{

	}

	public List<ItemStack> getRecipeCatalystItemStacks()
	{
		return new ArrayList<>();
	}

	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
	{
		RecipeType<R> recipeType = this.getRecipeType();

		for (ItemStack itemStack : this.getRecipeCatalystItemStacks())
		{
			registry.addRecipeCatalyst(itemStack, recipeType);
		}

	}

	public void registerGuiHandlers(IGuiHandlerRegistration registry)
	{

	}

}
