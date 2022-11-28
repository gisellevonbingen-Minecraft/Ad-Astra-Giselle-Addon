package ad_astra_giselle_addon.common.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.registries.AddonBlocks;
import earth.terrarium.ad_astra.client.screens.GuiUtil;
import earth.terrarium.ad_astra.registry.ModTags;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin
{
	public static final String JEI_CATEGORY = "jei.category";
	public static final String JEI_INFO = "jei.info";
	public static final String JEI_TOOLTIP = "jei.tooltip";

	private static JeiPlugin instance;

	public static JeiPlugin instance()
	{
		return instance;
	}

	public static ResourceLocation createUid(ResourceLocation key)
	{
		return new ResourceLocation(key.getNamespace(), JEI_CATEGORY + "." + key.getPath());
	}

	public static Component getCategoryTitle(ResourceLocation key)
	{
		return Component.translatable(JEI_CATEGORY + "." + key.getNamespace() + "." + key.getPath());
	}

	private final List<RecipeCategory<?>> categories;
	private RecipeCategoryFuelLoader fuelLoaderCategory;

	private IDrawable fluidOverlay;

	public JeiPlugin()
	{
		instance = this;

		this.categories = new ArrayList<>();
	}

	@Override
	public ResourceLocation getPluginUid()
	{
		return AdAstraGiselleAddon.rl("jei");
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration)
	{
		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
		this.categories.clear();
		this.categories.add(this.fuelLoaderCategory = new RecipeCategoryFuelLoader(Fluid.class));
		this.fluidOverlay = guiHelper.drawableBuilder(GuiUtil.FLUID_TANK_TEXTURE, 0, 0, GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT).setTextureSize(GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT).build();

		for (RecipeCategory<?> recipeCategory : this.getCategories())
		{
			recipeCategory.createGui(guiHelper);
			registration.addRecipeCategories(recipeCategory);
		}

	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategories())
		{
			recipeCategory.addRecipeTransferHandler(registration);
		}

	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategories())
		{
			recipeCategory.registerRecipeCatalysts(registration);
		}

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategories())
		{
			recipeCategory.registerGuiHandlers(registration);
		}

	}

	@Override
	public void registerRecipes(IRecipeRegistration registration)
	{
		for (RecipeCategory<?> recipeCategory : this.getCategories())
		{
			recipeCategory.registerRecipes(registration);
		}

		this.addIngredientInfo(registration, AddonBlocks.FUEL_LOADER.get(), MachinesConfig.FUEL_LOADER_WORKING_RANGE, ModTags.FUELS.location());
	}

	public void addIngredientInfo(IRecipeRegistration registration, ItemLike itemLike, Object... objects)
	{
		registration.addIngredientInfo(new ItemStack(itemLike), VanillaTypes.ITEM_STACK, Component.translatable(AdAstraGiselleAddon.tl(JEI_INFO, ForgeRegistries.ITEMS.getKey(itemLike.asItem())), objects));
	}

	public List<RecipeCategory<?>> getCategories()
	{
		return Collections.unmodifiableList(this.categories);
	}

	public RecipeCategoryFuelLoader getFuelLoaderCategory()
	{
		return this.fuelLoaderCategory;
	}

	public IDrawable getFluidOverlay()
	{
		return this.fluidOverlay;
	}

}
