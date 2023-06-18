package ad_astra_giselle_addon.common.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.CreativeModeTab.Builder;

public class CreativeModeTabHelperDelegate implements CreativeModeTabHelper.Delegate
{
	@Override
	public Builder builder()
	{
		return FabricItemGroup.builder();
	}

}
