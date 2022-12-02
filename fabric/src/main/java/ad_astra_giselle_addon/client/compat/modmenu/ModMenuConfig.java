package ad_astra_giselle_addon.client.compat.modmenu;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuConfig implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory()
	{
		return parent ->
		{
			ResourcefulConfig config = AdAstraGiselleAddon.config();
			return config != null ? new ConfigScreen(null, config) : null;
		};
	}

}
