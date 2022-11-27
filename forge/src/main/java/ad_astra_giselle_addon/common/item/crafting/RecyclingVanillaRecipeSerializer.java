package ad_astra_giselle_addon.common.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class RecyclingVanillaRecipeSerializer<P extends AbstractCookingRecipe, R extends AbstractCookingRecipe & IRecyclingVanillaRecipe<P, Container>> implements RecipeSerializer<R>
{
	private final SimpleCookingSerializer<P> parent;
	private final CookieBaker<P, R> factory;

	public RecyclingVanillaRecipeSerializer(SimpleCookingSerializer<P> parent, RecyclingVanillaRecipeSerializer.CookieBaker<P, R> factory)
	{
		this.parent = parent;
		this.factory = factory;
	}

	@Override
	public R fromJson(ResourceLocation id, JsonObject json)
	{
		JsonElement resultJson = json.get("result");

		try
		{
			json.addProperty("result", "minecraft:air");
			ItemOutput output = ItemOutput.fromJson(resultJson);
			P parentRecipe = this.parent.fromJson(id, json);
			return this.factory.create(parentRecipe, output);
		}
		finally
		{
			json.add("result", resultJson);
		}

	}

	@Override
	public R fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
	{
		P parentRecipe = this.parent.fromNetwork(id, buffer);
		ItemOutput output = ItemOutput.fromNetwork(buffer);
		return this.factory.create(parentRecipe, output);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, R recipe)
	{
		this.parent.toNetwork(buffer, recipe.getParent());
		recipe.getOutput().toNetwork(buffer);
	}

	public static interface CookieBaker<P extends AbstractCookingRecipe, R extends AbstractCookingRecipe & IRecyclingVanillaRecipe<P, Container>>
	{
		R create(P parent, ItemOutput output);
	}

}
