package ad_astra_giselle_addon.client.compat.rei;

import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.client.compat.RecipeHelper;
import ad_astra_giselle_addon.client.screen.FuelLoaderScreen;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import ad_astra_giselle_addon.common.registry.ObjectRegistry;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;

public class FuelLoaderCategory extends AddonDisplayCategory<FuelLoaderDisplay>
{
	public FuelLoaderCategory(CategoryIdentifier<FuelLoaderDisplay> identifier)
	{
		super(identifier);
	}

	@Override
	public Renderer getIcon()
	{
		return EntryStacks.of(AddonBlocks.FUEL_LOADER);
	}

	@Override
	public int getDisplayWidth(FuelLoaderDisplay display)
	{
		return RecipeHelper.FuelLoader.BACKGROUND_WIDTH;
	}

	@Override
	public int getDisplayHeight()
	{
		return RecipeHelper.FuelLoader.BACKGROUND_HEIGHT;
	}

	@Override
	public List<Widget> setupDisplay(FuelLoaderDisplay display, Rectangle bounds)
	{
		long capacity = FluidHooks.buckets(1);
		Rectangle tankBounds = new Rectangle(bounds.x + RecipeHelper.FuelLoader.TANK_LEFT, bounds.y + RecipeHelper.FuelLoader.TANK_TOP, RecipeHelper.FuelLoader.TANK_WIDTH, RecipeHelper.FuelLoader.TANK_HEIGHT);

		List<Widget> list = new ArrayList<>();
		list.add(Widgets.createTexturedWidget(RecipeHelper.FuelLoader.BACKGROUND_LOCATION, bounds.x, bounds.y, RecipeHelper.FuelLoader.BACKGROUND_WIDTH, RecipeHelper.FuelLoader.BACKGROUND_HEIGHT));
		list.add(new FluidTankWidget(tankBounds).entry(EntryStacks.of(display.fluid(), capacity)));
		return list;
	}

	@Override
	public List<ItemStack> getWorkStationItemStacks()
	{
		List<ItemStack> list = super.getWorkStationItemStacks();
		list.add(new ItemStack(AddonBlocks.FUEL_LOADER.get()));
		return list;
	}

	@Override
	public void registerRecipes(DisplayRegistry registry)
	{
		super.registerRecipes(registry);

		ObjectRegistry.get(Registry.FLUID_REGISTRY).stream().filter(RecipeHelper.FuelLoader::testFluid).map(FuelLoaderDisplay::new).forEach(registry::add);
	}

	@Override
	public void registerGuiHandlers(ScreenRegistry registry)
	{
		super.registerGuiHandlers(registry);

		FuelLoaderGuiContainerHandler handler = new FuelLoaderGuiContainerHandler();
		registry.registerClickArea(FuelLoaderScreen.class, handler);
		registry.registerFocusedStack(handler);
	}

}
