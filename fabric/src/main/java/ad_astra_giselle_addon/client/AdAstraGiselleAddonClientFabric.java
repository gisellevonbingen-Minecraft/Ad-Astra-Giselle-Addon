package ad_astra_giselle_addon.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;

public class AdAstraGiselleAddonClientFabric implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		AdAstraGiselleAddonClient.initializeClient();
		ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((spriteAtlasTexture, registry) -> AdAstraGiselleAddonClient.registerBlockAtlas(registry::register));
		AdAstraGiselleAddonClient.registerOverlay((id, hud) -> HudRenderCallback.EVENT.register(hud::renderHud));
		AdAstraGiselleAddonClient.registerBlockEntityRenderer(BlockEntityRenderers::register);
		AdAstraGiselleAddonClient.registerItemTooltip(register -> ItemTooltipCallback.EVENT.register(register::accept));

		AdAstraGiselleAddonClient.registerReloadListeners((id, listener) -> ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener()
		{
			@Override
			public ResourceLocation getFabricId()
			{
				return AdAstraGiselleAddon.rl(id);
			}

			@Override
			public CompletableFuture<Void> reload(PreparationBarrier synchronizer, ResourceManager manager, ProfilerFiller prepareProfiler, ProfilerFiller applyProfiler, Executor prepareExecutor, Executor applyExecutor)
			{
				return listener.reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
			}
		}));

		AddonClientKeyBindings.register(KeyBindingHelper::registerKeyBinding);
		ClientTickEvents.START_CLIENT_TICK.register($ -> AddonClientKeyBindings.updateKeys());
	}

}
