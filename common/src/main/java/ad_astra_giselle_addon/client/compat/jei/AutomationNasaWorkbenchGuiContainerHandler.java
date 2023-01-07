package ad_astra_giselle_addon.client.compat.jei;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.client.screen.AutomationNasaWorkbenchScreen;
import earth.terrarium.ad_astra.common.compat.jei.category.NasaWorkbenchCategory;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

public class AutomationNasaWorkbenchGuiContainerHandler implements IGuiContainerHandler<AutomationNasaWorkbenchScreen>
{
	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(AutomationNasaWorkbenchScreen screen, double mouseX, double mouseY)
	{
		return Collections.singleton(new IGuiClickableArea()
		{
			@Override
			public Rect2i getArea()
			{
				return GuiClickableAreaHelper.getInGuiBounds(screen, screen.getArrowBounds());
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui)
			{
				recipesGui.showTypes(Collections.singletonList(NasaWorkbenchCategory.RECIPE));
			}

			@Override
			public List<Component> getTooltipStrings()
			{
				return GuiClickableAreaHelper.getShowRecipesTooltip(screen.getCookTimeTooltip());
			}
		});

	}

}
