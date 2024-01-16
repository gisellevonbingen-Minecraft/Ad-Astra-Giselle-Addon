package ad_astra_giselle_addon.common.crafting;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import ad_astra_giselle_addon.common.mixin.mixin.minecraft.ShapedRecipeAccessor;
import ad_astra_giselle_addon.common.registry.AddonRecipeSerializers;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class CanUpgradeRecipe extends ShapedRecipe
{
	public CanUpgradeRecipe(ShapedRecipe parent)
	{
		super(parent.getId(), parent.getGroup(), parent.category(), parent.getWidth(), parent.getHeight(), parent.getIngredients(), ((ShapedRecipeAccessor) parent).getResult(), parent.showNotification());
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return AddonRecipeSerializers.CAN_UPGRADING.get();
	}

	@Override
	public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess)
	{
		ItemStack result = super.assemble(pContainer, pRegistryAccess);
		IOxygenCharger to = OxygenChargerUtils.get(new ItemStackHolder(result));

		if (to != null)
		{
			for (ItemStack item : pContainer.getItems())
			{
				IOxygenCharger from = OxygenChargerUtils.get(new ItemStackHolder(item.copy()));

				if (from != null)
				{
					to.setChargeMode(from.getChargeMode());
					FluidUtils2.moveFluidAny(from.getFluidContainer(), to.getFluidContainer(), f -> true, false);
				}

			}

		}

		return result.copy();
	}

	public static class Serializer implements RecipeSerializer<CanUpgradeRecipe>
	{
		@Override
		public CanUpgradeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson)
		{
			ShapedRecipe parent = RecipeSerializer.SHAPED_RECIPE.fromJson(pRecipeId, pJson);
			return new CanUpgradeRecipe(parent);
		}

		@Override
		public @Nullable CanUpgradeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer)
		{
			ShapedRecipe parent = RecipeSerializer.SHAPED_RECIPE.fromNetwork(pRecipeId, pBuffer);
			return new CanUpgradeRecipe(parent);
		}

		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, CanUpgradeRecipe pRecipe)
		{
			RecipeSerializer.SHAPED_RECIPE.toNetwork(pBuffer, pRecipe);
		}

	}

}
