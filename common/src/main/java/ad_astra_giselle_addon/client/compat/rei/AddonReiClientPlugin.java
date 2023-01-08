package ad_astra_giselle_addon.client.compat.rei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.client.compat.RecipeHelper;
import ad_astra_giselle_addon.client.screen.AutomationNasaWorkbenchScreen;
import ad_astra_giselle_addon.common.compat.rei.AddonReiCommonPlugin;
import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import earth.terrarium.ad_astra.common.compat.rei.REICategories;
import earth.terrarium.ad_astra.common.registry.ModTags;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.world.level.ItemLike;

public class AddonReiClientPlugin implements REIClientPlugin
{
	private final List<AddonDisplayCategory<?>> categories;
	private FuelLoaderCategory fuelLoaderCategory;

	public AddonReiClientPlugin()
	{
		this.categories = new ArrayList<>();
		this.categories.add(this.fuelLoaderCategory = new FuelLoaderCategory(AddonReiCommonPlugin.FUEL_LOADER_CATEGORY));
	}

	@Override
	public void registerCategories(CategoryRegistry registry)
	{
		for (AddonDisplayCategory<?> category : this.getCategories())
		{
			registry.add(category);
			registry.addWorkstations(category.getCategoryIdentifier(), category.getWorkStationItemStacks().stream().map(EntryStacks::of).toList().toArray(new EntryStack[0]));
		}

		registry.addWorkstations(REICategories.NASA_WORKBENCH_CATEGORY, EntryStacks.of(AddonBlocks.AUTOMATION_NASA_WORKBENCH));
	}

	@Override
	public void registerTransferHandlers(TransferHandlerRegistry registry)
	{
		for (AddonDisplayCategory<?> category : this.getCategories())
		{
			category.addTransferHandler(registry);
		}

	}

	@Override
	public void registerScreens(ScreenRegistry registry)
	{
		for (AddonDisplayCategory<?> category : this.getCategories())
		{
			category.registerGuiHandlers(registry);
		}

		registry.registerClickArea(AutomationNasaWorkbenchScreen.class, new AutomationNasaWorkbenchGuiContainerHandler());
	}

	@Override
	public void registerDisplays(DisplayRegistry registry)
	{
		for (AddonDisplayCategory<?> category : this.getCategories())
		{
			category.registerRecipes(registry);
		}

		registry.add(this.createDisplayInfo(AddonBlocks.FUEL_LOADER.get(), MachinesConfig.FUEL_LOADER_WORKING_RANGE, ModTags.FUELS.location()));
	}

	public DefaultInformationDisplay createDisplayInfo(ItemLike itemLike, Object... objects)
	{
		DefaultInformationDisplay info = DefaultInformationDisplay.createFromEntry(EntryStacks.of(itemLike), RecipeHelper.getInfoTitle(itemLike));
		info.lines(RecipeHelper.getInfoBody(itemLike, objects));
		return info;
	}

	public List<AddonDisplayCategory<?>> getCategories()
	{
		return Collections.unmodifiableList(this.categories);
	}

	public FuelLoaderCategory getFuelLoaderCategory()
	{
		return this.fuelLoaderCategory;
	}

}
