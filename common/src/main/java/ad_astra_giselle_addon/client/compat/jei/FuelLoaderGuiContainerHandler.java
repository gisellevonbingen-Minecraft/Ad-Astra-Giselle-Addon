package ad_astra_giselle_addon.client.compat.jei;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ad_astra_giselle_addon.client.screen.FuelLoaderScreen;
import ad_astra_giselle_addon.client.screen.GuiUtils2;
import earth.terrarium.adastra.client.components.machines.FluidBarWidget;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import mezz.jei.api.gui.builder.IClickableIngredientFactory;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.runtime.IClickableIngredient;
import mezz.jei.common.util.ImmutableRect2i;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

public class FuelLoaderGuiContainerHandler extends AddonGuiContainerHandler<FuelLoaderScreen>
{
	private final IJeiHelpers jeiHelpers;

	public FuelLoaderGuiContainerHandler(IJeiHelpers jeiHelpers)
	{
		this.jeiHelpers = jeiHelpers;
	}

	@Override
	public Rectangle getRecipeClickableAreaBounds(FuelLoaderScreen screen)
	{
		FluidBarWidget widget = screen.getFluidBarWidget(0);

		if (widget != null)
		{
			return GuiUtils2.getBounds(widget);
		}
		else
		{
			return new Rectangle();
		}

	}

	@Override
	protected RecipeType<?> getRecipeType(FuelLoaderScreen screen)
	{
		return AddonJeiPlugin.instance().getFuelLoaderCategory().getRecipeType();
	}

	@Override
	public List<Component> getRecipeTooltip(FuelLoaderScreen screen)
	{
		return Collections.emptyList();
	}

	@Override
	public Optional<? extends IClickableIngredient<?>> getClickableIngredientUnderMouse(IClickableIngredientFactory builder, FuelLoaderScreen screen, double mouseX, double mouseY)
	{
		Rectangle fluidTankBounds = this.getRecipeClickableAreaBounds(screen);

		if (fluidTankBounds.contains(mouseX, mouseY))
		{
			ResourceStack<FluidResource> fluid = screen.getMenu().getEntity().getFluids(null).getContents(0);
			Object ingredient = IJeiFluidStackHelper.INSTANCE.get(fluid);
			return this.wrap(ingredient, fluidTankBounds);
		}

		return super.getClickableIngredientUnderMouse(builder, screen, mouseX, mouseY);
	}

	public Optional<IClickableIngredient<?>> wrap(Object ingredient, Rectangle bounds)
	{
		return this.jeiHelpers.getIngredientManager().createTypedIngredient(ingredient, false).map(typedIngredient ->
		{
			return new ClickableIngredient<>(typedIngredient, new ImmutableRect2i(bounds.x, bounds.y, bounds.width, bounds.height));
		});

	}

	public class ClickableIngredient<T> implements IClickableIngredient<T>
	{
		private final ITypedIngredient<T> value;
		private final ImmutableRect2i area;

		public ClickableIngredient(ITypedIngredient<T> value, ImmutableRect2i area)
		{
			this.value = value;
			this.area = area;
		}

		@Override
		public ITypedIngredient<T> getTypedIngredient()
		{
			return value;
		}

		@Override
		public Rect2i getArea()
		{
			return area.toMutable();
		}

	}

}
