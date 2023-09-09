package ad_astra_giselle_addon.client.compat.jei;

import java.awt.Rectangle;
import java.util.List;

import ad_astra_giselle_addon.client.screen.FuelLoaderScreen;
import earth.terrarium.ad_astra.common.compat.jei.guihandler.BaseGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;

public class FuelLoaderGuiContainerHandler extends BaseGuiContainerHandler<FuelLoaderScreen>
{
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

	@SuppressWarnings("deprecation")
	@Override
	public Object getIngredientUnderMouse(FuelLoaderScreen screen, double mouseX, double mouseY)
	{
		if (screen.getFluidTankBounds().contains(mouseX, mouseY))
		{
			return IJeiFluidStackHelper.INSTANCE.get(screen.getFluid().getFluid(), screen.getFluid().getFluidAmount());
		}

		return super.getIngredientUnderMouse(screen, mouseX, mouseY);
	}

}
