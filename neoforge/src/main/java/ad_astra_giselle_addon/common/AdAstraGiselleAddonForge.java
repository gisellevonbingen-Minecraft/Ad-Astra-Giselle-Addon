package ad_astra_giselle_addon.common;

import ad_astra_giselle_addon.client.AdAstraGiselleAddonClientForge;
import ad_astra_giselle_addon.common.capability.SidedInWrapperProvider;
import ad_astra_giselle_addon.common.config.AddonForgeConfigs;
import ad_astra_giselle_addon.common.item.SidedItemContainerBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(AdAstraGiselleAddon.MOD_ID)
public class AdAstraGiselleAddonForge
{
	public AdAstraGiselleAddonForge()
	{
		AdAstraGiselleAddon.initializeCommon();
		AdAstraGiselleAddon.registerConfig(AddonForgeConfigs.class);

		IEventBus fml_bus = ModLoadingContext.get().getActiveContainer().getEventBus();
		fml_bus.addListener(this::onBlockEntityAttachCapabilities);

		IEventBus forge_bus = NeoForge.EVENT_BUS;
		forge_bus.addListener((RegisterCommandsEvent e) -> AdAstraGiselleAddon.registerCommand(e.getDispatcher()::register));

		if (FMLEnvironment.dist.isClient())
		{
			AdAstraGiselleAddonClientForge.init();
		}

	}

	public void onBlockEntityAttachCapabilities(RegisterCapabilitiesEvent event)
	{
		BuiltInRegistries.BLOCK_ENTITY_TYPE.forEach(blockEntityType ->
		{
			event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, blockEntityType, (blockEntity, object2) ->
			{
				if (blockEntity instanceof SidedItemContainerBlock itemContainer)
				{
					return new SidedInWrapperProvider(itemContainer).getCapability(blockEntity, object2);
				}

				return null;
			});
		});

	}

}
