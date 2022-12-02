package ad_astra_giselle_addon.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;

@Config(AddonConfigs.ID)
public final class AddonForgeConfigs
{
	@InlineCategory
	public static ItemsConfig ITEMS;

	@InlineCategory
	public static MachinesConfig MACHINES;

	@InlineCategory
	public static EnchantmentsConfig ENCHANTMENTS;

	@InlineCategory
	public static ForgeCompatsConfig COMPATS;
}
