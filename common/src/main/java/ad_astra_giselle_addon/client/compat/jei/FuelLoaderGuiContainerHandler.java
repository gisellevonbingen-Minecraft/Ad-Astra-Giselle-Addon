package ad_astra_giselle_addon.client.compat.jei;

import java.awt.Rectangle;
import java.util.List;
import java.util.Optional;

import ad_astra_giselle_addon.client.screen.FuelLoaderScreen;
import earth.terrarium.ad_astra.common.compat.jei.guihandler.BaseGuiContainerHandler;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.runtime.IClickableIngredient;
import mezz.jei.common.input.ClickableIngredient;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.library.ingredients.TypedIngredient;
import net.minecraft.network.chat.Component;

public class FuelLoaderGuiContainerHandler extends BaseGuiContainerHandler<FuelLoaderScreen>
{
	private final IJeiHelpers jeiHelpers;

	public FuelLoaderGuiContainerHandler(IJeiHelpers jeiHelpers)
	{
		this.jeiHelpers = jeiHelpers;
	}

	@Override
	public Rectangle getRecipeClickableAreaBounds(FuelLoaderScreen screen)
	{
		return screen.getFluidTankBounds();
	}

	@Override
	protected RecipeType<?> getRecipeType(FuelLoaderScreen screen)
	{
		return AddonJeiPlugin.instance().getFuelLoaderCategory().getRecipeType();
	}

	@Override
	public List<Component> getRecipeTooltip(FuelLoaderScreen screen)
	{
		return screen.getFluidTankTooltip();
	}

	@Override
	public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(FuelLoaderScreen screen, double mouseX, double mouseY)
	{
		Rectangle fluidTankBounds = screen.getFluidTankBounds();

		if (fluidTankBounds.contains(mouseX, mouseY))
		{
			Object ingredient = IJeiFluidStackHelper.INSTANCE.get(screen.getFluid());
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
