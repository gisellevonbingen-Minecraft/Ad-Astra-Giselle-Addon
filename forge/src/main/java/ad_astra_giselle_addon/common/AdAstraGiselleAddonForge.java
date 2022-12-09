package ad_astra_giselle_addon.common;

import ad_astra_giselle_addon.client.AdAstraGiselleAddonClientForge;
import ad_astra_giselle_addon.common.config.AddonForgeConfigs;
import ad_astra_giselle_addon.common.delegate.CreativeModeTabBuilder;
import ad_astra_giselle_addon.common.delegate.PlatformCommonDelegate;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(AdAstraGiselleAddon.MOD_ID)
public class AdAstraGiselleAddonForge implements PlatformCommonDelegate
{
	public AdAstraGiselleAddonForge()
	{
		IEventBus forge_bus = MinecraftForge.EVENT_BUS;

		AdAstraGiselleAddon.initializeCommon(this);
		AdAstraGiselleAddon.registerConfig(AddonForgeConfigs.class);

		forge_bus.addListener((RegisterCommandsEvent e) -> AdAstraGiselleAddon.registerCommand(e.getDispatcher()::register));

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AdAstraGiselleAddonClientForge::new);
	}

	@Override
	public CreativeModeTab createCreativeModeTab(CreativeModeTabBuilder builder)
	{
		ResourceLocation id = builder.id();
		return new CreativeModeTab(id.getNamespace() + "." + id.getPath())
		{
			@Override
			public void fillItemList(NonNullList<ItemStack> list)
			{
				builder.appendItems(list);
			}

			@Override
			public ItemStack makeIcon()
			{
				return builder.icon().get();
			}

		};

	}

}
