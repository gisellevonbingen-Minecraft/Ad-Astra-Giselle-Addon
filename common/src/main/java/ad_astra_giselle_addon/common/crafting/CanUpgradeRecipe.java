package ad_astra_giselle_addon.common.crafting;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec.MapCodecCodec;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.mixin.minecraft.ShapedRecipeAccessor;
import ad_astra_giselle_addon.common.registry.AddonRecipeSerializers;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class CanUpgradeRecipe extends ShapedRecipe
{
	private final ShapedRecipe parent;

	public CanUpgradeRecipe(ShapedRecipe parent)
	{
		super(parent.getGroup(), parent.category(), new ShapedRecipePattern(parent.getWidth(), parent.getHeight(), parent.getIngredients(), null), ((ShapedRecipeAccessor) parent).getResult(), parent.showNotification());
		this.parent = parent;
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
					to.copyFrom(from);
				}

			}

		}

		return result.copy();
	}

	public static class Serializer implements RecipeSerializer<CanUpgradeRecipe>
	{
		public static final Codec<CanUpgradeRecipe> CODEC = ((MapCodecCodec<ShapedRecipe>) RecipeSerializer.SHAPED_RECIPE.codec()).codec().xmap(CanUpgradeRecipe::new, r -> r.parent).codec();

		@Override
		public @Nullable CanUpgradeRecipe fromNetwork(FriendlyByteBuf pBuffer)
		{

			ShapedRecipe parent = RecipeSerializer.SHAPED_RECIPE.fromNetwork(pBuffer);
			return new CanUpgradeRecipe(parent);
		}

		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, CanUpgradeRecipe pRecipe)
		{
			RecipeSerializer.SHAPED_RECIPE.toNetwork(pBuffer, pRecipe);
		}

		@Override
		public Codec<CanUpgradeRecipe> codec()
		{
			return CODEC;
		}

	}

}
