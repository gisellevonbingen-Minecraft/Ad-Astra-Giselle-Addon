package ad_astra_giselle_addon.common.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;

public class CreativeModeTabHelperDelegate implements CreativeModeTabHelper.Delegate
{
	@Override
	public Builder builder()
	{
		return CreativeModeTab.builder();
	}

}
