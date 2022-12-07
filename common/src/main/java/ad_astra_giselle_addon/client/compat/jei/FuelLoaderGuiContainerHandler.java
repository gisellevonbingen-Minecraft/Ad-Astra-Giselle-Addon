package ad_astra_giselle_addon.client.compat.jei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.client.screens.FuelLoaderScreen;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

public class FuelLoaderGuiContainerHandler implements IGuiContainerHandler<FuelLoaderScreen>
{
	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(FuelLoaderScreen screen, double mouseX, double mouseY)
	{
		return Collections.singleton(new IGuiClickableArea()
		{
			@Override
			public Rect2i getArea()
			{
				Rectangle rect = screen.getFluidTankBounds();
				return new Rect2i(rect.x, rect.y, rect.width, rect.height);
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui)
			{
				recipesGui.showTypes(Collections.singletonList(AddonJeiPlugin.instance().getFuelLoaderCategory().getRecipeType()));
			}

			@Override
			public List<Component> getTooltipStrings()
			{
				List<Component> list = new ArrayList<>();
				list.add(screen.getFluidTankTooltip());
				list.add(Component.translatable("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

	@Override
	public Object getIngredientUnderMouse(FuelLoaderScreen screen, double mouseX, double mouseY)
	{
		if (screen.isFluidTankHovering())
		{
			FluidHolder fluid = screen.getFluid();
			return IJeiFluidStackHelper.INSTANCE.get(fluid.getFluid(), fluid.getFluidAmount());
		}

		return IGuiContainerHandler.super.getIngredientUnderMouse(screen, mouseX, mouseY);
	}

}
