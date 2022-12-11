package ad_astra_giselle_addon.client.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.client.compat.RecipeHelper;
import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.common.registry.ModTags;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

@JeiPlugin
public class AddonJeiPlugin implements IModPlugin
{
	public static final RecipeType<Fluid> FUEL_LOADER_CATEGORY = new RecipeType<>(AddonBlocks.FUEL_LOADER.getId(), Fluid.class);

	private static AddonJeiPlugin instance;

	public static AddonJeiPlugin instance()
	{
		return instance;
	}

	private final List<AddonRecipeCategory<?>> categories;
	private FuelLoaderCategory fuelLoaderCategory;

	private IDrawable fluidOverlay;

	public AddonJeiPlugin()
	{
		instance = this;
		this.categories = new ArrayList<>();
		this.categories.add(this.fuelLoaderCategory = new FuelLoaderCategory(FUEL_LOADER_CATEGORY));
	}

	@Override
	public ResourceLocation getPluginUid()
	{
		return AdAstraGiselleAddon.rl("jei");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		this.fluidOverlay = guiHelper.drawableBuilder(GuiUtil.FLUID_TANK_TEXTURE, 0, 0, GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT).setTextureSize(GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT).build();

		for (AddonRecipeCategory<?> category : this.getCategories())
		{
			category.createGui(guiHelper);
			registry.addRecipeCategories(category);
		}

	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry)
	{
		for (AddonRecipeCategory<?> category : this.getCategories())
		{
			category.addTransferHandler(registry);
		}

	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
	{
		for (AddonRecipeCategory<?> category : this.getCategories())
		{
			category.registerRecipeCatalysts(registry);
		}

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry)
	{
		for (AddonRecipeCategory<?> category : this.getCategories())
		{
			category.registerGuiHandlers(registry);
		}

	}

	@Override
	public void registerRecipes(IRecipeRegistration registry)
	{
		for (AddonRecipeCategory<?> category : this.getCategories())
		{
			category.registerRecipes(registry);
		}

		this.addIngredientInfo(registry, AddonBlocks.FUEL_LOADER.get(), MachinesConfig.FUEL_LOADER_WORKING_RANGE, ModTags.FUELS.location());
	}

	public void addIngredientInfo(IRecipeRegistration registration, ItemLike itemLike, Object... objects)
	{
		registration.addIngredientInfo(new ItemStack(itemLike), VanillaTypes.ITEM_STACK, RecipeHelper.getInfoBody(itemLike, objects));
	}

	public List<AddonRecipeCategory<?>> getCategories()
	{
		return Collections.unmodifiableList(this.categories);
	}

	public FuelLoaderCategory getFuelLoaderCategory()
	{
		return this.fuelLoaderCategory;
	}

	public IDrawable getFluidOverlay()
	{
		return this.fluidOverlay;
	}

}
