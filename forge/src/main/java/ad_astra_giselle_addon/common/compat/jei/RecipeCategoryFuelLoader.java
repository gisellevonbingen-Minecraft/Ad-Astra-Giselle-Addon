package ad_astra_giselle_addon.common.compat.jei;

import java.util.List;

import ad_astra_giselle_addon.client.screens.FuelLoaderScreen;
import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.config.AddonConfigs;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.registries.AddonBlocks;
import earth.terrarium.ad_astra.client.screens.GuiUtil;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeCategoryFuelLoader extends RecipeCategory<Fluid>
{
	public static final ResourceLocation BACKGROUND_LOCATION = AdAstraGiselleAddon.rl("textures/jei/fuel_loader.png");
	public static final int BACKGROUND_WIDTH = 144;
	public static final int BACKGROUND_HEIGHT = 84;
	public static final int TANK_LEFT = 53;
	public static final int TANK_TOP = 20;

	public static IDrawable createBackground(IGuiHelper guiHelper)
	{
		return guiHelper.createDrawable(BACKGROUND_LOCATION, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
	}

	private IDrawable background;

	public RecipeCategoryFuelLoader(Class<? extends Fluid> recipeClass)
	{
		super(new RecipeType<>(AddonBlocks.FUEL_LOADER.getId(), recipeClass));
	}

	@Override
	public void createGui(IGuiHelper guiHelper)
	{
		super.createGui(guiHelper);
		this.background = createBackground(guiHelper);
	}

	@Override
	public IDrawable getBackground()
	{
		return this.background;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, Fluid recipe, IFocusGroup focuses)
	{
		super.setRecipe(builder, recipe, focuses);

		long capacity = AddonConfigs.Common.machines.fuelLoader_capacity.get();

		builder.addSlot(RecipeIngredientRole.INPUT, TANK_LEFT, TANK_TOP) //
				.addFluidStack(recipe, capacity) //
				.setFluidRenderer(capacity, false, GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT) //
				.setOverlay(JeiPlugin.instance().getFluidOverlay(), 0, 0);
	}

	public boolean testFluid(Fluid fluid)
	{
		return FluidPredicates.isFuel(fluid) && fluid.isSource(fluid.defaultFluidState());
	}

	@Override
	public List<ItemStack> getRecipeCatalystItemStacks()
	{
		List<ItemStack> list = super.getRecipeCatalystItemStacks();
		list.add(new ItemStack(AddonBlocks.FUEL_LOADER.get()));
		return list;
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		super.registerRecipes(registration);

		registration.addRecipes(this.getRecipeType(), ForgeRegistries.FLUIDS.getValues().stream().filter(this::testFluid).toList());
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		super.registerGuiHandlers(registration);

		registration.addGuiContainerHandler(FuelLoaderScreen.class, new FuelLoaderGuiContainerHandler());
	}

}
