package ad_astra_giselle_addon.common;

import ad_astra_giselle_addon.client.AdAstraGiselleAddonClientForge;
import ad_astra_giselle_addon.common.capability.SidedInWrapperProvider;
import ad_astra_giselle_addon.common.config.AddonForgeConfigs;
import ad_astra_giselle_addon.common.item.SidedItemContainerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(AdAstraGiselleAddon.MOD_ID)
public class AdAstraGiselleAddonForge
{
	public AdAstraGiselleAddonForge()
	{
		IEventBus forge_bus = MinecraftForge.EVENT_BUS;

		AdAstraGiselleAddon.initializeCommon();
		AdAstraGiselleAddon.registerConfig(AddonForgeConfigs.class);

		forge_bus.addListener((RegisterCommandsEvent e) -> AdAstraGiselleAddon.registerCommand(e.getDispatcher()::register));
		forge_bus.addGenericListener(BlockEntity.class, this::onBlockEntityAttachCapabilities);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AdAstraGiselleAddonClientForge::new);
	}

	public void onBlockEntityAttachCapabilities(AttachCapabilitiesEvent<BlockEntity> event)
	{
		if (event.getObject() instanceof SidedItemContainerBlock itemContainer)
		{
			event.addCapability(AdAstraGiselleAddon.rl("item"), new SidedInWrapperProvider(itemContainer));
		}

	}

}
