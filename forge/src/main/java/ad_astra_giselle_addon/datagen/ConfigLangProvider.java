package ad_astra_giselle_addon.datagen;

import java.lang.reflect.Field;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfigEntry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.config.AddonConfigs;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ConfigLangProvider extends LanguageProvider
{
	public ConfigLangProvider(DataGenerator gen)
	{
		super(gen, AdAstraGiselleAddon.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations()
	{
		ResourcefulConfig config = AdAstraGiselleAddon.config();
		this.add(config, AddonConfigs.PREFIX);
	}

	private void add(ResourcefulConfig config, String prefix)
	{
		for (Entry<String, ? extends ResourcefulConfigEntry> entryPair : config.getEntries().entrySet())
		{
			String entryPath = prefix + "." + entryPair.getKey();
			Field field = entryPair.getValue().field();
			ConfigEntry entry = field.getAnnotation(ConfigEntry.class);

			if (!StringUtils.equals(entryPath, entry.translation()))
			{
				throw new RuntimeException("'" + entryPath + "' and '" + entry.translation() + "' is mismatched");
			}

			Comment comment = field.getAnnotation(Comment.class);

			if (comment != null)
			{
				String commentPath = entryPath + ".comment";

				if (!StringUtils.equals(commentPath, comment.translation()))
				{
					throw new RuntimeException("'" + commentPath + "' and '" + comment.translation() + "' is mismatched");
				}

				this.add(comment.translation(), comment.value());
			}

		}

		for (Entry<String, ? extends ResourcefulConfig> subConfigPair : config.getSubConfigs().entrySet())
		{
			this.add(subConfigPair.getValue(), prefix + "." + subConfigPair.getKey());
		}

	}

}
