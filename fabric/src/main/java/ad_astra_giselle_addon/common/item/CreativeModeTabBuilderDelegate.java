package ad_astra_giselle_addon.common.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeModeTabBuilderDelegate implements CreativeModeTabBuilder.Delegate
{
	@Override
	public CreativeModeTab build(CreativeModeTabBuilder builder)
	{
		return FabricItemGroupBuilder.create(builder.id()).icon(builder.icon()).appendItems(builder::appendItems).build();
	}

}
