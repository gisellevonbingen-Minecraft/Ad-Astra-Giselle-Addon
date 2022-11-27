package ad_astra_giselle_addon.common.config;

import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class RecipesConfig
{
	private static final List<String> DEFAULT_TAG_PREFERENCES = Arrays.asList("minecraft", "tconstruct", "tmechworks", "create", "immersiveengineering", "mekanism", "thermal");

	public final ConfigValue<List<? extends String>> tagPreferences;

	public final ConfigValue<Boolean> recycling_enabled;

	public RecipesConfig(ForgeConfigSpec.Builder builder)
	{
		this.tagPreferences = builder.defineList("tagPreferences", DEFAULT_TAG_PREFERENCES, str -> true);

		builder.push("recycling");

		this.recycling_enabled = builder.comment("Compressed and plate items can restore into be each ingot (a.k.a recycling)", "1. Minecraft - Smelting", "2. Minecraft - Blasting", "3. Immersive Enginering - Recycling").define("enabled", true);

		builder.pop();
	}

}
