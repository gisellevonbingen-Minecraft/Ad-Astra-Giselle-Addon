package ad_astra_giselle_addon.datagen;

import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.teamresourceful.resourcefulconfig.api.types.ResourcefulConfig;
import com.teamresourceful.resourcefulconfig.api.types.entries.ResourcefulConfigEntry;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryData;
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.config.AddonConfigs;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ConfigLangProvider extends LanguageProvider
{
	public ConfigLangProvider(PackOutput output)
	{
		super(output, AdAstraGiselleAddon.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations()
	{
		ResourcefulConfig config = AdAstraGiselleAddon.config();
		this.add(config, AddonConfigs.PREFIX);
	}

	private void add(ResourcefulConfig config, String prefix)
	{
		for (Entry<String, ? extends ResourcefulConfigEntry> entryPair : config.entries().entrySet())
		{
			String entryPath = prefix + "." + entryPair.getKey();
			EntryData options = entryPair.getValue().options();
			String id = options.title().value();

			if (!StringUtils.equals(entryPath, id))
			{
				throw new RuntimeException("'" + entryPath + "' and '" + id + "' is mismatched");
			}

			TranslatableValue declaredComment = options.comment();

			if (declaredComment != null)
			{
				String commentPath = entryPath + ".comment";

				if (!StringUtils.equals(commentPath, declaredComment.value()))
				{
					throw new RuntimeException("'" + commentPath + "' and '" + declaredComment + "' is mismatched");
				}

				this.add(declaredComment.value(), declaredComment.translation());
			}

		}

		for (Entry<String, ? extends ResourcefulConfig> category : config.categories().entrySet())
		{
			this.add(category.getValue(), prefix + "." + category.getKey());
		}

	}

}
