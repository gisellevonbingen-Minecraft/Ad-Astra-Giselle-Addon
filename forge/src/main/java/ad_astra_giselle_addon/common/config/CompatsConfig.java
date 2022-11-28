package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;

import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonPneumaticCraftConfig;

@Category(id = CompatsConfig.ID, translation = CompatsConfig.PREFIX)
public final class CompatsConfig
{
	public static final String ID = "compats";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;

	@InlineCategory
	public static AddonMekanismConfig MEKANISM;
	@InlineCategory
	public static AddonPneumaticCraftConfig PNEUMATICCRAFT;

}
