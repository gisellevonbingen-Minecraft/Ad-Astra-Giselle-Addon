package ad_astra_giselle_addon.datagen;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = AdAstraGiselleAddon.MOD_ID, bus = Bus.MOD)
public class AdAstraGiselleAddonDataGenerator
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
		PackOutput packOutput = gen.getPackOutput();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		gen.addProvider(event.includeClient(), new ConfigLangProvider(packOutput));
	}

	private AdAstraGiselleAddonDataGenerator()
	{

	}

}
