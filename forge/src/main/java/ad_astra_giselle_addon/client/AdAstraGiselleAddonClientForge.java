package ad_astra_giselle_addon.client;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class AdAstraGiselleAddonClientForge
{
	public AdAstraGiselleAddonClientForge()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		fml_bus.addListener((FMLClientSetupEvent e) -> AdAstraGiselleAddonClient.initializeClient());
		fml_bus.addListener((RegisterClientReloadListenersEvent e) -> AdAstraGiselleAddonClient.registerReloadListeners((id, listener) -> e.registerReloadListener(listener)));

		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.addListener((ItemTooltipEvent e) -> AdAstraGiselleAddonClient.registerItemTooltip(register -> register.accept(e.getItemStack(), e.getFlags(), e.getToolTip())));
		forge_bus.addListener(AdAstraGiselleAddonClientForge::onRegisterClientHud);

		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
		{
			return new ConfigScreenHandler.ConfigScreenFactory((client, parent) ->
			{
				return AdAstraGiselleAddonClient.getConfigScreen();
			});
		});

	}

	private static void onRegisterClientHud(RenderGuiEvent.Post event)
	{
		AdAstraGiselleAddonClient.onRegisterHud(hud -> hud.renderHud(event.getGuiGraphics(), event.getPartialTick()));
	}

}
