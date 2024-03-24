package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;

import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonPneumaticCraftConfig;

@Category(value = ForgeCompatsConfig.ID, categories = {AddonMekanismConfig.class, AddonPneumaticCraftConfig.class})
public final class ForgeCompatsConfig
{
	public static final String ID = "compats";
	public static final String PREFIX = AddonConfigs.PREFIX + "." + ID;
}
