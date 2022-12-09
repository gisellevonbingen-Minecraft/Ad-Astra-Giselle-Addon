package ad_astra_giselle_addon.common.item;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabBuilderDelegate implements CreativeModeTabBuilder.Delegate
{
	@Override
	public CreativeModeTab build(CreativeModeTabBuilder builder)
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
