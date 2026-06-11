package ad_astra_giselle_addon.datagen;

import java.util.concurrent.CompletableFuture;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = AdAstraGiselleAddon.MOD_ID)
public class AdAstraGiselleAddonDataGenerator
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
		PackOutput packOutput = gen.getPackOutput();
		CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
		@SuppressWarnings("unused")
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		gen.addProvider(event.includeClient(), new ConfigLangProvider(packOutput));

		GeneratedEntriesProvider generatedEntriesProvider = new GeneratedEntriesProvider(packOutput, lookupProvider);
		lookupProvider = generatedEntriesProvider.getRegistryProvider();
		gen.addProvider(event.includeServer(), generatedEntriesProvider);
	}

	private AdAstraGiselleAddonDataGenerator()
	{

	}

}
