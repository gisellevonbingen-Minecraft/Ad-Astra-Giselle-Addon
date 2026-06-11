package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;

import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonPneumaticCraftConfig;

@Category(value = ForgeCompatsConfig.ID, categories = {AddonMekanismConfig.class, AddonPneumaticCraftConfig.class})
@ConfigInfo(icon = "machines", title = "Forge Compat Config")
public final class ForgeCompatsConfig
{
	public static final String ID = "compats";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;
}
