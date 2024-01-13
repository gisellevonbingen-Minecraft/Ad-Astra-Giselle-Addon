package ad_astra_giselle_addon.common;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;

import ad_astra_giselle_addon.common.command.AddonCommand;
import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.config.AddonConfigs;
import ad_astra_giselle_addon.common.content.proof.AcidRainProofUtils;
import ad_astra_giselle_addon.common.content.proof.GravityNormalizingUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceFireProofUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import ad_astra_giselle_addon.common.registry.AddonTabs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;

public class AdAstraGiselleAddon
{
	public static final String MOD_ID = "ad_astra_giselle_addon";
	public static final Logger LOGGER = LogManager.getLogger();
	private static final Configurator CONFIGURATOR = new Configurator();

	private static EventBus eventBus;
	private static CompatibleManager compats;

	private static Class<?> configClass;

	public static CompatibleManager compats()
	{
		return compats;
	}

	public static ResourcefulConfig config()
	{
		return CONFIGURATOR.getConfig(configClass);
	}

	public static void registerConfig(Class<?> configClass)
	{
		AdAstraGiselleAddon.configClass = configClass;
		AddonConfigs.validConfig(configClass);
		CONFIGURATOR.registerConfig(configClass);
	}

	public static void initializeCommon()
	{
		AddonBlocks.BLOCKS.register();
		AddonItems.ITEMS.register();
		AddonTabs.TABS.register();
		AddonEnchantments.ENCHANTMENTS.register();
		AddonBlockEntityTypes.BLOCK_ENTITY_TYPES.register();
		AddonMenuTypes.MENU_TYPES.register();
		AddonNetwork.registerAll();

		eventBus = new EventBus();
		eventBus.register(SpaceOxygenProofUtils.INSTANCE);
		eventBus.register(SpaceFireProofUtils.INSTANCE);
		eventBus.register(AcidRainProofUtils.INSTANCE);
		eventBus.register(GravityNormalizingUtils.INSTANCE);

		compats = new CompatibleManager();
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
