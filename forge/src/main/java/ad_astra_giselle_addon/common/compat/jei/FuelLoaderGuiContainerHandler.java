package ad_astra_giselle_addon.common.compat.jei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.client.screens.FuelLoaderScreen;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.ad_astra.client.screens.GuiUtil;
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
	public Collection<IGuiClickableArea> getGuiClickableAreas(FuelLoaderScreen containerScreen, double mouseX, double mouseY)
	{
		return Collections.singleton(new IGuiClickableArea()
		{
			@Override
			public Rect2i getArea()
			{
				Rectangle rect = containerScreen.getFluidTankBounds();
				return new Rect2i(rect.x, rect.y, rect.width, rect.height);
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui)
			{
				recipesGui.showTypes(Collections.singletonList(JeiPlugin.instance().getFuelLoaderCategory().getRecipeType()));
			}

			@Override
			public List<Component> getTooltipStrings()
			{
				int tank = 0;
				List<Component> list = new ArrayList<>();
				FuelLoaderMenu menu = containerScreen.getMenu();
				FluidHolder fluid = menu.getFluids().get(tank);
				long capacity = menu.getMachine().getFluidContainer().getTankCapacity(tank);
				list.add(TranslationUtils.fluid(fluid, capacity));
				list.add(Component.translatable("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

	@Override
	public Object getIngredientUnderMouse(FuelLoaderScreen containerScreen, double mouseX, double mouseY)
	{
		Rectangle fluidTankBounds = containerScreen.getFluidTankBounds();

		if (GuiUtil.isHovering(fluidTankBounds, mouseX, mouseY))
		{
			return containerScreen.getMenu().getMachine().getFluidContainer().getFluids().get(0).getFluid();
		}

		return IGuiContainerHandler.super.getIngredientUnderMouse(containerScreen, mouseX, mouseY);
	}

}
