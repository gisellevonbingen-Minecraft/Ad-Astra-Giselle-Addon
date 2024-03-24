package ad_astra_giselle_addon.common.config;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.teamresourceful.resourcefulconfig.api.annotations.Config;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;

@Config(value = AddonConfigs.ID, categories = {ItemsConfig.class, MachinesConfig.class, EnchantmentsConfig.class})
public final class AddonConfigs
{
	public static final String ID = AdAstraGiselleAddon.MOD_ID + ".ver2";
	public static final String PREFIX = "config." + AdAstraGiselleAddon.MOD_ID;

	public static void validConfig(Class<?> clazz)
	{
		List<String> requireCategoires = Lists.newArrayList(getCatoryNames(AddonConfigs.class));
		List<String> currentCategoires = Lists.newArrayList(getCatoryNames(clazz));
		requireCategoires.removeAll(currentCategoires);

		if (requireCategoires.size() > 0)
		{
			throw new IllegalArgumentException("config class (" + clazz + ") has missing categoris: " + requireCategoires);
		}

	}

	private static String[] getCatoryNames(Class<?> clazz)
	{
		Config config = clazz.getDeclaredAnnotation(Config.class);
		return Stream.of(config.categories()).map(Class<?>::getName).toArray(String[]::new);
	}

}
