package ad_astra_giselle_addon.client;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;

import ad_astra_giselle_addon.client.overlay.OxygenCanOverlay;
import ad_astra_giselle_addon.client.renderer.blockentity.FuelLoaderRenderer;
import ad_astra_giselle_addon.client.screen.AutomationNasaWorkbenchScreen;
import ad_astra_giselle_addon.client.screen.FuelLoaderScreen;
import ad_astra_giselle_addon.client.util.RenderHelper;
import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.enchantment.EnchantmentHelper2;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import ad_astra_giselle_addon.common.util.TriConsumer;
import earth.terrarium.ad_astra.client.AdAstraClient.RenderHud;
import earth.terrarium.ad_astra.client.ClientUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AdAstraGiselleAddonClient
{
	private AdAstraGiselleAddonClient()
	{

	}

	@Nullable
	public static ConfigScreen getConfigScreen()
	{
		ResourcefulConfig config = AdAstraGiselleAddon.config();
		return config != null ? new ConfigScreen(null, config) : null;
	}

	public static void initializeClient()
	{
		ClientUtils.registerScreen(AddonMenuTypes.FUEL_LOADER.get(), FuelLoaderScreen::new);
		ClientUtils.registerScreen(AddonMenuTypes.AUTOMATION_NASA_WORKBENCH.get(), AutomationNasaWorkbenchScreen::new);
	}

	public static void registerBlockAtlas(Consumer<ResourceLocation> register)
	{
		register.accept(RenderHelper.TILE_SURFACE);
	}

	public static void registerOverlay(BiConsumer<String, RenderHud> register)
	{
		register.accept("oxygen_can", OxygenCanOverlay::renderHud);
	}

	public static void registerBlockEntityRenderer(BlockEntityRendererRegister register)
	{
		register.regsiter(AddonBlockEntityTypes.FUEL_LOADER.get(), context -> new FuelLoaderRenderer(context));
	}

	public static void registerItemTooltip(Consumer<TriConsumer<ItemStack, TooltipFlag, List<Component>>> register)
	{
		register.accept(EnchantedBookTooltipHelper::addTooltip);
	}

	public static void registerReloadListeners(BiConsumer<String, PreparableReloadListener> register)
	{
		register.accept("cleardescriptionscache", new ResourceManagerReloadListener()
		{
			@Override
			public void onResourceManagerReload(ResourceManager pResourceManager)
			{
				EnchantmentHelper2.clearDescriptionsCache();
			}
		});

	}

	public interface BlockEntityRendererRegister
	{
		<T extends BlockEntity> void regsiter(BlockEntityType<? extends T> type, BlockEntityRendererProvider<T> renderer);
	}

}
