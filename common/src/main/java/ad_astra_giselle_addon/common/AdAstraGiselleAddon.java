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
import ad_astra_giselle_addon.common.content.proof.SpaceFireProofUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import ad_astra_giselle_addon.common.delegate.DelegateRegistryHelper;
import ad_astra_giselle_addon.common.delegate.PlatformCommonDelegate;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.registries.AddonAttributes;
import ad_astra_giselle_addon.common.registries.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registries.AddonBlocks;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import ad_astra_giselle_addon.common.registries.AddonItems;
import ad_astra_giselle_addon.common.registries.AddonMenuTypes;
import ad_astra_giselle_addon.common.registries.AddonTabs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;

public class AdAstraGiselleAddon
{
	public static final String MOD_ID = "ad_astra_giselle_addon";
	public static final Logger LOGGER = LogManager.getLogger();
	private static final Configurator CONFIGURATOR = new Configurator();

	private static PlatformCommonDelegate delegate;
	private static EventBus eventBus;
	private static CompatibleManager compats;

	private static Class<?> configClass;

	public static EventBus eventBus()
	{
		return eventBus;
	}

	public static PlatformCommonDelegate delegate()
	{
		return delegate;
	}

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

	public static void initializeCommon(PlatformCommonDelegate delegate)
	{
		AdAstraGiselleAddon.delegate = delegate;

		DelegateRegistryHelper registryHelper = delegate.getRegistryHelper();
		AddonTabs.visit();
		AddonBlocks.BLOCKS.register(registryHelper);
		AddonItems.ITEMS.register(registryHelper);
		AddonEnchantments.ENCHANTMENTS.register(registryHelper);
		AddonBlockEntityTypes.BLOCK_ENTITY_TYPES.register(registryHelper);
		AddonMenuTypes.MENU_TYPES.register(registryHelper);
		AddonAttributes.ATTRIBUTES.register(registryHelper);
		AddonNetwork.registerAll();

		eventBus = new EventBus();
		eventBus.register(SpaceOxygenProofUtils.INSTANCE);
		eventBus.register(SpaceFireProofUtils.INSTANCE);
		eventBus.register(AcidRainProofUtils.INSTANCE);

		compats = new CompatibleManager(delegate.getAddonHelper().getAddons());
		compats.tryLoad(delegate);
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

	private AdAstraGiselleAddon()
	{

	}

}
