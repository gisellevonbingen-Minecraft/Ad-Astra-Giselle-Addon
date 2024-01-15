package ad_astra_giselle_addon.common.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;

@Config(AddonConfigs.ID)
public final class AddonConfigs
{
	public static final String ID = AdAstraGiselleAddon.MOD_ID + ".ver2";
	public static final String PREFIX = "config." + AdAstraGiselleAddon.MOD_ID;

	@InlineCategory
	public static ItemsConfig ITEMS;

	@InlineCategory
	public static MachinesConfig MACHINES;

	@InlineCategory
	public static EnchantmentsConfig ENCHANTMENTS;

	public static void validConfig(Class<?> clazz)
	{
		List<String> requireFields = getCatoryFieldNames(AddonConfigs.class);
		List<String> currentFields = getCatoryFieldNames(clazz);
		requireFields.removeAll(currentFields);

		if (requireFields.size() > 0)
		{
			throw new IllegalArgumentException("config class (" + clazz + ") has missing categoris: " + requireFields);
		}

	}

	private static List<String> getCatoryFieldNames(Class<?> clazz)
	{
		return new ArrayList<>(Arrays.stream(clazz.getDeclaredFields()).filter(f -> f.getAnnotation(InlineCategory.class) != null).map(Field::getName).toList());
	}

}
