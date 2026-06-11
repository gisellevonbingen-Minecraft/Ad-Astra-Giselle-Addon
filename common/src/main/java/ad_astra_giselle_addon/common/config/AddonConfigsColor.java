package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.api.types.info.ResourcefulConfigColorGradient;

public class AddonConfigsColor implements ResourcefulConfigColorGradient
{
	public static final AddonConfigsColor INSTANCE = new AddonConfigsColor();

	@Override
	public String first()
	{
		return "#7F4DEE";
	}

	@Override
	public String second()
	{
		return "#E7797A";
	}

	@Override
	public String degree()
	{
		return "45deg";
	}

}
