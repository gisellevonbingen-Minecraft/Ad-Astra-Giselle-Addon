package ad_astra_giselle_addon.common.delegate;

import net.minecraft.world.item.CreativeModeTab;

public interface PlatformCommonDelegate
{
	DelegateAddonProvider getAddonHelper();

	CreativeModeTab createCreativeModeTab(CreativeModeTabBuilder builder);
}
