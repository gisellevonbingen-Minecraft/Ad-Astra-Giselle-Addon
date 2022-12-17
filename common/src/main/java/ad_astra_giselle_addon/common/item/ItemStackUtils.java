package ad_astra_giselle_addon.common.item;

import net.minecraft.world.item.ItemStack;

public class ItemStackUtils
{
	public static ItemStack deriveCount(ItemStack item, int newCount)
	{
		ItemStack copy = item.copy();
		copy.setCount(newCount);
		return copy;
	}

}
