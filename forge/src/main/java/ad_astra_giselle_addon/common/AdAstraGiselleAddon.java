package ad_astra_giselle_addon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ad_astra_giselle_addon.client.AdAstraGiselleAddonClientProxy;
import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.config.AddonConfigs;
import ad_astra_giselle_addon.common.content.oxygen.EventListenerOxygenCharger;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.item.crafting.TagPreference;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.registries.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registries.AddonBlocks;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import ad_astra_giselle_addon.common.registries.AddonItems;
import ad_astra_giselle_addon.common.registries.AddonMenuTypes;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AdAstraGiselleAddon.MOD_ID)
public class AdAstraGiselleAddon
{
	public static final String PMODID = AdAstra.MOD_ID;
	public static final String MOD_ID = "ad_astra_giselle_addon";
	public static final Logger LOGGER = LogManager.getLogger();

	public AdAstraGiselleAddon()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AddonConfigs.CommonSpec);

		registerFML();
		registerForge();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AdAstraGiselleAddonClientProxy::new);

		CompatibleManager.visit();
	}

	public static void registerFML()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonBlocks.BLOCKS.register(fml_bus);
		AddonItems.ITEMS.register(fml_bus);
		AddonEnchantments.ENCHANTMENTS.register(fml_bus);
		AddonBlockEntityTypes.BLOCK_ENTITY_TYPES.register(fml_bus);
		AddonMenuTypes.MENU_TYPES.register(fml_bus);

		AddonNetwork.registerAll();
	}

	public static void registerForge()
	{
		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(EventListenerCommand.class);
		forge_bus.register(EventListenerOxygenCharger.class);

		ProofAbstractUtils.register(forge_bus);
		TagPreference.register(forge_bus);
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

	public static ResourceLocation prl(String path)
	{
		return new ResourceLocation(PMODID, path);
	}

}
