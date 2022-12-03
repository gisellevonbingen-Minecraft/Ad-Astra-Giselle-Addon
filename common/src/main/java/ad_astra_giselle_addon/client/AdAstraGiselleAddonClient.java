package ad_astra_giselle_addon.client;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;

import ad_astra_giselle_addon.client.overlay.OxygenCanOverlay;
import ad_astra_giselle_addon.client.renderer.blockentity.FuelLoaderRenderer;
import ad_astra_giselle_addon.client.screens.FuelLoaderScreen;
import ad_astra_giselle_addon.client.util.RenderHelper;
import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registries.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registries.AddonMenuTypes;
import earth.terrarium.ad_astra.client.AdAstraClient.RenderHud;
import earth.terrarium.ad_astra.client.ClientUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
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
	}

	public static void registerBlockAtlas(Consumer<ResourceLocation> register)
	{
		register.accept(RenderHelper.TILE_SURFACE);
	}

	public static void registerBlockEntityRenderer(BlockEntityRendererRegister register)
	{
		register.regsiter(AddonBlockEntityTypes.FUEL_LOADER.get(), context -> new FuelLoaderRenderer(context));
	}

	public static void registerOverlay(BiConsumer<String, RenderHud> register)
	{
		register.accept("oxygen_can", OxygenCanOverlay::renderHud);
	}

	public interface BlockEntityRendererRegister
	{
		<T extends BlockEntity> void regsiter(BlockEntityType<? extends T> type, BlockEntityRendererProvider<T> renderer);
	}

}
