package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColor;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigLink;
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue;

public class AddonConfigsInfo implements ResourcefulConfigInfo
{
	public static final TranslatableValue TITLE = new TranslatableValue("Ad Astra Giselle Addon", "config.ad_astra.title");

	public static final TranslatableValue DESCRIPTION = new TranslatableValue("Test.", "config.ad_astra.description");

	@Override
	public TranslatableValue title()
	{
		return TITLE;
	}

	@Override
	public TranslatableValue description()
	{
		return DESCRIPTION;
	}

	@Override
	public String icon()
	{
		return "planet";
	}

	@Override
	public ResourcefulConfigColor color()
	{
		return AddonConfigsColor.INSTANCE;
	}

	@Override
	public ResourcefulConfigLink[] links()
	{
		return new ResourcefulConfigLink[0];
	}

	@Override
	public boolean isHidden()
	{
		return false;
	}

}
