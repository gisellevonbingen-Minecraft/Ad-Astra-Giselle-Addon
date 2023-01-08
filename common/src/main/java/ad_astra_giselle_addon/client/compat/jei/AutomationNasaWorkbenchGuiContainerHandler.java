package ad_astra_giselle_addon.client.compat.jei;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.client.screen.AutomationNasaWorkbenchScreen;
import earth.terrarium.ad_astra.common.compat.jei.category.NasaWorkbenchCategory;
import earth.terrarium.ad_astra.common.compat.jei.guihandler.BaseGuiContainerHandler;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;

public class AutomationNasaWorkbenchGuiContainerHandler extends BaseGuiContainerHandler<AutomationNasaWorkbenchScreen>
{
	@Override
	public Rectangle getRecipeClickableAreaBounds(AutomationNasaWorkbenchScreen screen)
	{
		return screen.getArrowBounds();
	}

	@Override
	protected RecipeType<?> getRecipeType(AutomationNasaWorkbenchScreen screen)
	{
		return NasaWorkbenchCategory.RECIPE;
	}

	@Override
	public List<Component> getRecipeTooltip(AutomationNasaWorkbenchScreen screen)
	{
		return Collections.singletonList(screen.getCookTimeTooltip());
	}

}
