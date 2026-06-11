package ad_astra_giselle_addon.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class AdAstraGiselleAddonClientForge
{
	public AdAstraGiselleAddonClientForge()
	{
		IEventBus fml_bus = ModLoadingContext.get().getActiveContainer().getEventBus();
		fml_bus.addListener((FMLClientSetupEvent e) -> AdAstraGiselleAddonClient.initializeClient());
		fml_bus.addListener((RegisterMenuScreensEvent e) -> AdAstraGiselleAddonClient.registerScreens(e));
		fml_bus.addListener((RegisterClientReloadListenersEvent e) -> AdAstraGiselleAddonClient.registerReloadListeners((id, listener) -> e.registerReloadListener(listener)));
		fml_bus.addListener((EntityRenderersEvent.RegisterRenderers e) -> AdAstraGiselleAddonClient.registerBlockEntityRenderer(e::registerBlockEntityRenderer));

		IEventBus forge_bus = NeoForge.EVENT_BUS;
		forge_bus.addListener((ItemTooltipEvent e) -> AdAstraGiselleAddonClient.registerItemTooltip(register -> register.accept(new ItemTooltipModifier(e.getItemStack(), e.getFlags(), e.getToolTip(), e.getContext()))));
		forge_bus.addListener(AdAstraGiselleAddonClientForge::onRegisterClientHud);

		ModLoadingContext.get().getActiveContainer().registerExtensionPoint(IConfigScreenFactory.class, (container, screen) ->
		{
			return AdAstraGiselleAddonClient.getConfigScreen(screen);
		});

	}

	private static void onRegisterClientHud(RenderGuiEvent.Post event)
	{
		AdAstraGiselleAddonClient.onRegisterHud(hud -> hud.renderHud(event.getGuiGraphics(), event.getPartialTick()));
	}

}
