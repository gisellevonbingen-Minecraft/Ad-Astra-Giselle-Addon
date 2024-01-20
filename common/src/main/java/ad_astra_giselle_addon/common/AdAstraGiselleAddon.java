package ad_astra_giselle_addon.common;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;

import ad_astra_giselle_addon.common.command.AddonCommand;
import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.config.AddonConfigs;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import ad_astra_giselle_addon.common.registry.AddonProofs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;

public class AdAstraGiselleAddon
{
	public static final String MOD_ID = "ad_astra_giselle_addon";
	public static final Logger LOGGER = LogManager.getLogger();
	private static final Configurator CONFIGURATOR = new Configurator();

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
		AddonConfigs.validConfig(configClass);
		CONFIGURATOR.registerConfig(configClass);
	}

	public static void initializeCommon()
	{
		AddonBlocks.BLOCKS.register();
		AddonItems.ITEMS.register();
		AddonEnchantments.ENCHANTMENTS.register();
		AddonBlockEntityTypes.BLOCK_ENTITY_TYPES.register();
		AddonMenuTypes.MENU_TYPES.register();
		AddonNetwork.registerAll();
		AddonProofs.registerAll();

		COMPATS = new CompatibleManager();
	}

	public static void registerCommand(Consumer<LiteralArgumentBuilder<CommandSourceStack>> register)
	{
		register.accept(AddonCommand.builder());
	}

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MOD_ID, path);
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
