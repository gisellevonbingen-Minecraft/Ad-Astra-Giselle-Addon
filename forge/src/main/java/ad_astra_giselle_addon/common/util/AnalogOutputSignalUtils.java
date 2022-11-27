package ad_astra_giselle_addon.common.util;

import javax.annotation.Nullable;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class AnalogOutputSignalUtils
{
	public static int getAnalogOutputSignal(@Nullable IItemHandler inv)
	{
		return ItemHandlerHelper.calcRedstoneFromInventory(inv);
	}

	public static int getAnalogOutputSignal(@Nullable ItemStack itemStack)
	{
		if (itemStack == null || itemStack.isEmpty())
		{
			return 0;
		}
		else
		{
			float proportion = (float) itemStack.getCount() / itemStack.getMaxStackSize();
			return 1 + Mth.floor(14.0F * proportion);
		}

	}

	private AnalogOutputSignalUtils()
	{

	}

}
