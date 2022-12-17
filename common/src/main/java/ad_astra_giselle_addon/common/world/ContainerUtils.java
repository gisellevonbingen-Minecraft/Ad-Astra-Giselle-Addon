package ad_astra_giselle_addon.common.world;

import ad_astra_giselle_addon.common.item.ItemStackUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class ContainerUtils
{
	public static ItemStack insert(Container container, int slot, ItemStack item, boolean simulate)
	{
		ItemStack containing = container.getItem(slot);
		int grow = Mth.clamp(item.getCount(), 0, item.getMaxStackSize() - containing.getCount());

		if (containing.isEmpty() || (ItemStack.matches(containing, item) && grow > 0))
		{
			if (!simulate)
			{
				ItemStack newContaining = ItemStackUtils.deriveCount(item, containing.getCount() + grow);
				container.setItem(slot, newContaining);
			}

			return ItemStackUtils.deriveCount(item, item.getCount() - grow);
		}

		return item;
	}

}
