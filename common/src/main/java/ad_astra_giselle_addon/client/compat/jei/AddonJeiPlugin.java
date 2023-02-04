package ad_astra_giselle_addon.client.compat.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.client.compat.RecipeHelper;
import ad_astra_giselle_addon.client.screen.AutomationNasaWorkbenchScreen;
import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import earth.terrarium.ad_astra.common.compat.jei.category.NasaWorkbenchCategory;
import earth.terrarium.ad_astra.common.compat.jei.transfer.MachineTransferInfo;
import earth.terrarium.ad_astra.common.registry.ModTags;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
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

		registry.addRecipeTransferHandler(new MachineTransferInfo<>(AutomationNasaWorkbenchMenu.class, AddonMenuTypes.AUTOMATION_NASA_WORKBENCH.get(), NasaWorkbenchCategory.RECIPE)
		{
			@Override
			protected int getInputSlotCount(AutomationNasaWorkbenchMenu menu)
			{
				return 14;
			}
		});
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
	{
		for (AddonRecipeCategory<?> category : this.getCategories())
		{
			category.registerRecipeCatalysts(registry);
		}

		registry.addRecipeCatalyst(new ItemStack(AddonBlocks.AUTOMATION_NASA_WORKBENCH), NasaWorkbenchCategory.RECIPE);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry)
	{
		for (AddonRecipeCategory<?> category : this.getCategories())
		{
			category.registerGuiHandlers(registry);
		}

		registry.addGuiContainerHandler(AutomationNasaWorkbenchScreen.class, new AutomationNasaWorkbenchGuiContainerHandler());
	}

	@Override
	public void registerRecipes(IRecipeRegistration registry)
	{
		for (AddonRecipeCategory<?> category : this.getCategories())
		{
			category.registerRecipes(registry);
		}

		this.addIngredientInfo(registry, AddonBlocks.FUEL_LOADER.get(), MachinesConfig.FUEL_LOADER_WORKING_RANGE, ModTags.FUELS.location());
		this.addIngredientInfo(registry, AddonBlocks.GRAVITY_NORMALIZER.get());
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

}
