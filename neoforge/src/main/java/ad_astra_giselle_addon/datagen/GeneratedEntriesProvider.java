package ad_astra_giselle_addon.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class GeneratedEntriesProvider extends DatapackBuiltinEntriesProvider
{
	private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(//
			Registries.ENCHANTMENT, AddonEnchantments::bootstrap)//
	;

	public GeneratedEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
	{
		super(output, registries, BUILDER, Set.of(AdAstraGiselleAddon.MOD_ID));
	}

	@Override
	public String getName()
	{
		return AdAstraGiselleAddon.MOD_ID + " Generated Registry Entries";
	}

}
