package ad_astra_giselle_addon.common.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;

public class AddonConfigs
{
	public static final CommonConfig Common;
	public static final ForgeConfigSpec CommonSpec;

	static
	{
		Pair<CommonConfig, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
		Common = common.getLeft();
		CommonSpec = common.getRight();
	}

}
