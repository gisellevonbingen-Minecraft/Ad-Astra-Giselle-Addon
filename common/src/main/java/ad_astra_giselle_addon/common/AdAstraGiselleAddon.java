package ad_astra_giselle_addon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.teamresourceful.resourcefulconfig.common.config.Configurator;

import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.config.AddonConfigs;
import ad_astra_giselle_addon.common.content.proof.SpaceFireProofUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import ad_astra_giselle_addon.common.content.proof.VenusAcidProofUtils;
import ad_astra_giselle_addon.common.delegate.DelegateRegistryFactory;
import ad_astra_giselle_addon.common.delegate.PlatformCommonDelegate;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.registries.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registries.AddonBlocks;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import ad_astra_giselle_addon.common.registries.AddonItems;
import ad_astra_giselle_addon.common.registries.AddonMenuTypes;
import ad_astra_giselle_addon.common.registries.AddonTabs;
import net.minecraft.resources.ResourceLocation;

public class AdAstraGiselleAddon
{
	public static final String MOD_ID = "ad_astra_giselle_addon";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final Configurator CONFIGURATOR = new Configurator();

	private static PlatformCommonDelegate delegate;
	private static EventBus eventBus;
	private static CompatibleManager compats;

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

	public static void initCommon(PlatformCommonDelegate delegate)
	{
		CONFIGURATOR.registerConfig(AddonConfigs.class);

		AdAstraGiselleAddon.delegate = delegate;

		DelegateRegistryFactory registerFactory = delegate.getRegistryFactory();
		AddonTabs.visit();
		AddonBlocks.BLOCKS.register(registerFactory);
		AddonItems.ITEMS.register(registerFactory);
		AddonEnchantments.ENCHANTMENTS.register(registerFactory);
		AddonBlockEntityTypes.BLOCK_ENTITY_TYPES.register(registerFactory);
		AddonMenuTypes.MENU_TYPES.register(registerFactory);

		AddonNetwork.registerAll();

		eventBus = new EventBus();
		eventBus.register(SpaceOxygenProofUtils.INSTANCE);
		eventBus.register(SpaceFireProofUtils.INSTANCE);
		eventBus.register(VenusAcidProofUtils.INSTANCE);

		compats = new CompatibleManager(delegate.getAddonHelper().getAddons());
		compats.tryLoad(delegate);
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
