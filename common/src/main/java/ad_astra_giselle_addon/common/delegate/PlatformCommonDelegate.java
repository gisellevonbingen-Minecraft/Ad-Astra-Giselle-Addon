package ad_astra_giselle_addon.common.delegate;

import net.minecraft.world.item.CreativeModeTab;

public interface PlatformCommonDelegate
{
	DelegateAddonProvider getAddonHelper();

	DelegateRegistryHelper getRegistryHelper();

	CreativeModeTab createCreativeModeTab(CreativeModeTabBuilder builder);

	DelegateLivingHelper getLivingHelper();
}
