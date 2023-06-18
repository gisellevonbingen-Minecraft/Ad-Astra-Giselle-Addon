package ad_astra_giselle_addon.common.registry;

import net.minecraft.world.item.CreativeModeTab;

public class CreativeModeTabHelper
{
	public static final CreativeModeTabHelper.Delegate DELEGATE = new CreativeModeTabHelperDelegate();

	public static CreativeModeTab.Builder builder()
	{
		return DELEGATE.builder();
	}

	public static interface Delegate
	{
		CreativeModeTab.Builder builder();
	}

}
