package ad_astra_giselle_addon.common.entity;

import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.common.compat.CompatibleManagerDelegate;
import ad_astra_giselle_addon.common.compat.curios.CuriosHelper;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import earth.terrarium.common_storage_lib.item.wrappers.CommonItemContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public class LivingHelperDelegate implements LivingHelper.Delegate
{
	@Override
	public List<StorageSlotContext> getExtraEquipmentSlots(LivingEntity living)
	{
		List<StorageSlotContext> list = new ArrayList<>();
		list.addAll(this.getCuriosSlots(living));
		return list;
	}

	public List<StorageSlotContext> getCuriosSlots(LivingEntity living)
	{
		List<StorageSlotContext> list = new ArrayList<>();

		if (CompatibleManagerDelegate.CURIOS.isLoaded())
		{
			IItemHandlerModifiable itemHandler = CuriosHelper.getEquippedCurios(living);

			if (itemHandler != null)
			{
				CommonItemContainer container = new CommonItemContainer(itemHandler);

				for (int i = 0; i < itemHandler.getSlots(); i++)
				{
					ItemStack item = itemHandler.getStackInSlot(i);

					if (!item.isEmpty())
					{
						list.add(StorageSlotContext.ofSlot(container, container.get(i)));
					}

				}

			}

		}

		return list;
	}

}
