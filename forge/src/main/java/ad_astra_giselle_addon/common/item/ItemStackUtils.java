package ad_astra_giselle_addon.common.item;

import net.minecraft.world.item.ItemStack;

public class ItemStackUtils
{
	public static boolean hasUseableResources(ItemStack stack)
	{
		for (ItemUsableResource resource : ItemUsableResource.values())
		{
			if (resource.test(stack))
			{
				return true;
			}

		}

		return false;
	}

	public static ItemUsableResourceResult useResources(ItemStack stack, int energy, int durability, boolean simulate)
	{
		if (ItemUsableResource.Energy.use(stack, energy, simulate))
		{
			return new ItemUsableResourceResult(ItemUsableResource.Energy, energy, true);
		}
		else if (ItemUsableResource.Durability.use(stack, durability, simulate))
		{
			return new ItemUsableResourceResult(ItemUsableResource.Durability, durability, true);
		}
		else
		{
			return new ItemUsableResourceResult(ItemUsableResource.None, 0, false);
		}

	}

}
