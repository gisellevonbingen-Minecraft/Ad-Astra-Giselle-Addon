package ad_astra_giselle_addon.datagen;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

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
