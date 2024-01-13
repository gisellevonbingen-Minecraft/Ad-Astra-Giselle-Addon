package ad_astra_giselle_addon.client.compat.rei;

import java.awt.Rectangle;

import ad_astra_giselle_addon.client.screen.AutomationNasaWorkbenchScreen;
import earth.terrarium.adastra.common.compat.rei.categories.NasaWorkbenchCategory;

public class AutomationNasaWorkbenchGuiContainerHandler extends AddonClickArea<AutomationNasaWorkbenchScreen>
{
	@Override
	public Rectangle getBounds(AutomationNasaWorkbenchScreen screen)
	{
		return screen.getArrowBounds();
	}

	@Override
	public Result getSuccess(AutomationNasaWorkbenchScreen screen)
	{
		return categoryWithTooltip(NasaWorkbenchCategory.ID, screen.getCookTimeTooltip());
	}

}
