package ad_astra_giselle_addon.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.world.inventory.InventoryMenu;

public class AdAstraGiselleAddonClientFabric implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		AdAstraGiselleAddonClient.initializeClient();
		ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((spriteAtlasTexture, registry) -> AdAstraGiselleAddonClient.registerBlockAtlas(registry::register));
		AdAstraGiselleAddonClient.registerOverlay((id, hud) -> HudRenderCallback.EVENT.register(hud::renderHud));
		AdAstraGiselleAddonClient.registerBlockEntityRenderer(BlockEntityRendererRegistry::register);
	}

}
