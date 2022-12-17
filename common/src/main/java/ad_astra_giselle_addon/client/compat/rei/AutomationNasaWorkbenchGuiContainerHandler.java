package ad_astra_giselle_addon.client.compat.rei;

import ad_astra_giselle_addon.client.screen.AutomationNasaWorkbenchScreen;
import earth.terrarium.ad_astra.common.compat.rei.REICategories;
import me.shedaniel.rei.api.client.registry.screen.ClickArea;

public class AutomationNasaWorkbenchGuiContainerHandler implements ClickArea<AutomationNasaWorkbenchScreen>
{
	@Override
	public Result handle(ClickAreaContext<AutomationNasaWorkbenchScreen> context)
	{
		AutomationNasaWorkbenchScreen screen = context.getScreen();

		if (ClickAreaHelper.contains(screen.getArrowBounds(), context.getMousePosition()))
		{
			return ClickAreaHelper.categoryWithTooltip(REICategories.NASA_WORKBENCH_CATEGORY, screen.getCookTimeTooltip());
		}
		else
		{
			return Result.fail();
		}

	}

}
