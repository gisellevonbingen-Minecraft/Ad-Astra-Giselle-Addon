package ad_astra_giselle_addon.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class AdAstraGiselleAddonClientForge
{
	public static void init()
	{
		IEventBus fml_bus = ModLoadingContext.get().getActiveContainer().getEventBus();
		fml_bus.addListener((FMLClientSetupEvent e) -> AdAstraGiselleAddonClient.initializeClient());
		fml_bus.addListener((RegisterClientReloadListenersEvent e) -> AdAstraGiselleAddonClient.registerReloadListeners((id, listener) -> e.registerReloadListener(listener)));

		IEventBus forge_bus = NeoForge.EVENT_BUS;
		forge_bus.addListener((ItemTooltipEvent e) -> AdAstraGiselleAddonClient.registerItemTooltip(register -> register.accept(e.getItemStack(), e.getFlags(), e.getToolTip())));
		forge_bus.addListener(AdAstraGiselleAddonClientForge::onRegisterClientHud);

		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
		{
			return new ConfigScreenHandler.ConfigScreenFactory((client, parent) ->
			{
				return AdAstraGiselleAddonClient.getConfigScreen(parent);
			});
		});

	}

	private static void onRegisterClientHud(RenderGuiEvent.Post event)
	{
		AdAstraGiselleAddonClient.onRegisterHud(hud -> hud.renderHud(event.getGuiGraphics(), event.getPartialTick()));
	}

}
