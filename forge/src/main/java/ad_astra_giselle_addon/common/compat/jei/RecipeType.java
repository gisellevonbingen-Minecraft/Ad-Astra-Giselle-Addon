package ad_astra_giselle_addon.common.compat.jei;

import net.minecraft.resources.ResourceLocation;

public class RecipeType<T>
{
	private final ResourceLocation uid;
	private final Class<? extends T> recipeClass;

	public RecipeType(ResourceLocation uid, Class<? extends T> recipeClass)
	{
		this.uid = uid;
		this.recipeClass = recipeClass;
	}

	public ResourceLocation getUid()
	{
		return this.uid;
	}

	public Class<? extends T> getRecipeClass()
	{
		return this.recipeClass;
	}

}
