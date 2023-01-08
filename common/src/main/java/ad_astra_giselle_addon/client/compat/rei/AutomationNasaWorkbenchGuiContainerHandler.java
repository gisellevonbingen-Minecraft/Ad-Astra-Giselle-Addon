package ad_astra_giselle_addon.client.compat.rei;

import java.awt.Rectangle;

import ad_astra_giselle_addon.client.screen.AutomationNasaWorkbenchScreen;
import earth.terrarium.ad_astra.common.compat.rei.BaseClickArea;
import earth.terrarium.ad_astra.common.compat.rei.REICategories;

public class AutomationNasaWorkbenchGuiContainerHandler extends BaseClickArea<AutomationNasaWorkbenchScreen>
{
	@Override
	public Rectangle getBounds(AutomationNasaWorkbenchScreen screen)
	{
		return screen.getArrowBounds();
	}

	@Override
	public Result getSuccess(AutomationNasaWorkbenchScreen screen)
	{
		return categoryWithTooltip(REICategories.NASA_WORKBENCH_CATEGORY, screen.getCookTimeTooltip());
	}

}
