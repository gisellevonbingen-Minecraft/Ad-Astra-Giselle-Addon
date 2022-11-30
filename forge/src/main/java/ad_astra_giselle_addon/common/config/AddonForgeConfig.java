package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;

@Config(AddonConfigs.ID)
public class AddonForgeConfig
{

	@InlineCategory
	public static ForgeCompatsConfig COMPATS;
}
