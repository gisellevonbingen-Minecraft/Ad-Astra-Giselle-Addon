package ad_astra_giselle_addon.datagen;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map.Entry;

import com.teamresourceful.resourcefulconfig.api.types.ResourcefulConfig;
import com.teamresourceful.resourcefulconfig.api.types.entries.ResourcefulConfigEntry;
import com.teamresourceful.resourcefulconfig.api.types.entries.ResourcefulConfigFieldBackedValueEntry;
import com.teamresourceful.resourcefulconfig.api.types.entries.ResourcefulConfigObjectEntry;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryData;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.config.AddonConfigs;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ConfigLangProvider extends LanguageProvider
{
	private final HashSet<Field> fields = new HashSet<>();

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
			this.add(entryPair.getValue(), prefix + "." + entryPair.getKey());
		}

		for (Entry<String, ? extends ResourcefulConfig> subConfigPair : config.categories().entrySet())
		{
			this.add(subConfigPair.getValue(), prefix + "." + subConfigPair.getKey());
		}

	}

	private void add(ResourcefulConfigEntry entry, String prefix)
	{
		EntryData entryData = entry.options();

		if (entryData.comment().hasTranslation())
		{
			var added = false;

			if (entry instanceof ResourcefulConfigFieldBackedValueEntry fieldEntry)
			{
				if (!this.fields.add(fieldEntry.field()))
				{
					added = true;
				}

			}

			if (!added)
			{
				this.add(entryData.comment().translation(), entryData.comment().value());
			}

		}

		if (entry instanceof ResourcefulConfigObjectEntry objectEntry)
		{
			for (Entry<String, ? extends ResourcefulConfigEntry> entryPair2 : objectEntry.entries().entrySet())
			{
				this.add(entryPair2.getValue(), prefix + "." + entryPair2.getKey());
			}

		}

	}

}
