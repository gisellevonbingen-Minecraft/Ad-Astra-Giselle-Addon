package ad_astra_giselle_addon.common;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import com.teamresourceful.resourcefulconfig.api.types.ResourcefulConfig;

import ad_astra_giselle_addon.common.block.entity.AutomationNasaWorkbenchBlockEntity;
import ad_astra_giselle_addon.common.command.AddonCommand;
import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import ad_astra_giselle_addon.common.registry.AddonDataComponentTypes;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import ad_astra_giselle_addon.common.registry.AddonProofs;
import ad_astra_giselle_addon.common.registry.AddonRecipeSerializers;
import ad_astra_giselle_addon.common.registry.AddonTabs;
import earth.terrarium.common_storage_lib.item.ItemApi;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;

public class AdAstraGiselleAddon
{
	public static final String MOD_ID = "ad_astra_giselle_addon";
	public static final Logger LOGGER = LogManager.getLogger();
	private static final Configurator CONFIGURATOR = new Configurator(AdAstraGiselleAddon.MOD_ID);

	private static CompatibleManager COMPATS;

	private static Class<?> CONFIG_CLASS;

	public static CompatibleManager compats()
	{
		return COMPATS;
	}

	public static ResourcefulConfig config()
	{
		return CONFIGURATOR.getConfig(CONFIG_CLASS);
	}

	public static void registerConfig(Class<?> configClass)
	{
		AdAstraGiselleAddon.CONFIG_CLASS = configClass;
		// AddonConfigs.validConfig(configClass);
		CONFIGURATOR.register(configClass);
	}

	public static void initializeCommon()
	{
		AddonBlocks.BLOCKS.register();
		AddonItems.ITEMS.register();
		AddonTabs.TABS.register();
		AddonBlockEntityTypes.BLOCK_ENTITY_TYPES.register();
		AddonMenuTypes.MENU_TYPES.register();
		AddonDataComponentTypes.DATA_COMPONENT_TYPES.register();
		AddonRecipeSerializers.RECIPE_SERIALIZERS.init();
		AddonNetwork.registerAll();
		AddonProofs.registerAll();

		ItemApi.BLOCK.registerFallback((i, d) -> i instanceof AutomationNasaWorkbenchBlockEntity e ? e.getItems(d) : null);

		COMPATS = new CompatibleManager();
	}

	public static void registerCommand(Consumer<LiteralArgumentBuilder<CommandSourceStack>> register)
	{
		register.accept(AddonCommand.builder());
	}

	public static ResourceLocation rl(String path)
	{
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static String tl(String category, String path)
	{
		return category + "." + MOD_ID + "." + path;
	}

	public static String tl(String category, ResourceLocation rl)
	{
		return category + "." + rl.getNamespace() + "." + rl.getPath();
	}

	public static String tl(String category, ResourceLocation rl, String path)
	{
		return tl(category, rl) + "." + path;
	}

	private AdAstraGiselleAddon()
	{

	}

}
