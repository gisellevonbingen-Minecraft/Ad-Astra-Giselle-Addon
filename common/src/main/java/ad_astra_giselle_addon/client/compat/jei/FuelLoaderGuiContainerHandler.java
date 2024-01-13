package ad_astra_giselle_addon.client.compat.jei;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ad_astra_giselle_addon.client.screen.FuelLoaderScreen;
import earth.terrarium.adastra.client.components.machines.FluidBarWidget;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.runtime.IClickableIngredient;
import mezz.jei.common.input.ClickableIngredient;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.library.ingredients.TypedIngredient;
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
			return screen.getBounds(widget);
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
	public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(FuelLoaderScreen screen, double mouseX, double mouseY)
	{
		Rectangle fluidTankBounds = this.getRecipeClickableAreaBounds(screen);

		if (fluidTankBounds.contains(mouseX, mouseY))
		{
			FluidHolder fluid = screen.getMenu().getEntity().getFluidContainer().getFluids().get(0);
			Object ingredient = IJeiFluidStackHelper.INSTANCE.get(fluid);
			return this.wrap(ingredient, fluidTankBounds);
		}

		return super.getClickableIngredientUnderMouse(screen, mouseX, mouseY);
	}

	public Optional<IClickableIngredient<?>> wrap(Object ingredient, Rectangle bounds)
	{
		return TypedIngredient.createAndFilterInvalid(this.jeiHelpers.getIngredientManager(), ingredient).map(typedIngredient ->
		{
			return new ClickableIngredient<>(typedIngredient, new ImmutableRect2i(bounds.x, bounds.y, bounds.width, bounds.height));
		});

	}

}
