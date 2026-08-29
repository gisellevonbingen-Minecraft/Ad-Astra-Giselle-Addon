package ad_astra_giselle_addon.common.crafting;

import java.util.function.UnaryOperator;

import com.mojang.serialization.MapCodec;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import ad_astra_giselle_addon.common.mixin.minecraft.ShapedRecipeAccessor;
import ad_astra_giselle_addon.common.registry.AddonRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class CanUpgradeRecipe extends ShapedRecipe
{
	public CanUpgradeRecipe(ShapedRecipe parent)
	{
		super(parent.getGroup(), parent.category(), ((ShapedRecipeAccessor) parent).getPattern(), ((ShapedRecipeAccessor) parent).getResult(), parent.showNotification());
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return AddonRecipeSerializers.CAN_UPGRADING.get();
	}

	@Override
	public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries)
	{
		StorageSlotContext result = StorageSlotContext.ofIsolated(super.assemble(input, registries));
		IOxygenCharger to = OxygenChargerUtils.get(result);

		if (to != null)
		{
			for (int i = 0; i < input.size(); i++)
			{
				IOxygenCharger from = OxygenChargerUtils.get(StorageSlotContext.ofIsolated(input.getItem(i)));

				if (from != null)
				{
					to.setChargeMode(from.getChargeMode());
					FluidUtils2.moveFluidAny(from.getFluidContainer(), to.getFluidContainer(), f -> true, false);
				}

			}

		}

		return result.getItemStack();
	}

	public static class Serializer implements RecipeSerializer<CanUpgradeRecipe>
	{
		public static final MapCodec<CanUpgradeRecipe> CODEC = RecipeSerializer.SHAPED_RECIPE.codec().xmap(CanUpgradeRecipe::new, UnaryOperator.identity());
		public static final StreamCodec<RegistryFriendlyByteBuf, CanUpgradeRecipe> STREAM_CODEC = RecipeSerializer.SHAPED_RECIPE.streamCodec().map(CanUpgradeRecipe::new, UnaryOperator.identity());

		@Override
		public MapCodec<CanUpgradeRecipe> codec()
		{
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, CanUpgradeRecipe> streamCodec()
		{
			return STREAM_CODEC;
		}

	}

}
