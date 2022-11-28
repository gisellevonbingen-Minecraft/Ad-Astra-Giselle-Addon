package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;

@Config(AddonConfigs.ID)
public final class AddonConfigs
{
	public static final String ID = AdAstraGiselleAddon.MOD_ID;
	public static final String PREFIX = "config." + ID;

	@InlineCategory
	public static ItemsConfig ITEMS;

	@InlineCategory
	public static MachinesConfig MACHINES;

	@InlineCategory
	public static EnchantmentsConfig ENCHANTMENTS;

	@InlineCategory
	public static CompatsConfig COMPATS;
}
