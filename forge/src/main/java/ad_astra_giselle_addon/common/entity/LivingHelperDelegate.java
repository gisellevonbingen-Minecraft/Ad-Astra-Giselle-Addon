package ad_astra_giselle_addon.common.entity;

import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.common.compat.CompatibleManagerDelegate;
import ad_astra_giselle_addon.common.compat.curios.CuriosHelper;
import ad_astra_giselle_addon.common.item.ItemStackConsumers;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class LivingHelperDelegate implements LivingHelper.Delegate
{
	@Override
	public List<ItemStackReference> getExtraSlotItems(LivingEntity living)
	{
		List<ItemStackReference> list = new ArrayList<>();
		list.addAll(this.getCuriousStacks(living));
		return list;
	}

	public List<ItemStackReference> getCuriousStacks(LivingEntity living)
	{
		List<ItemStackReference> list = new ArrayList<>();

		if (CompatibleManagerDelegate.CURIOS.isLoaded())
		{
			IItemHandlerModifiable itemHandler = CuriosHelper.getEquippedCurios(living);

			if (itemHandler != null)
			{
				for (int i = 0; i < itemHandler.getSlots(); i++)
				{
					ItemStack item = itemHandler.getStackInSlot(i);

					if (!item.isEmpty())
					{
						list.add(new ItemStackReference(item, ItemStackConsumers.index(i, itemHandler::setStackInSlot)));
					}

				}

			}

		}

		return list;
	}

}
