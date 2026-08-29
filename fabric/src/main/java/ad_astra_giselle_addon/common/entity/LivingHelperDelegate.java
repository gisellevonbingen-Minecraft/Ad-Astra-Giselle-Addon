package ad_astra_giselle_addon.common.entity;

import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.common.compat.CompatibleManagerDelegate;
import ad_astra_giselle_addon.common.compat.trinkets.TrinketsHelper;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import earth.terrarium.common_storage_lib.item.impl.vanilla.WrappedVanillaContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LivingHelperDelegate implements LivingHelper.Delegate
{
	@Override
	public List<StorageSlotContext> getExtraEquipmentSlots(LivingEntity living)
	{
		List<StorageSlotContext> list = new ArrayList<>();
		list.addAll(this.getTrinketsStacks(living));
		return list;
	}

	public List<StorageSlotContext> getTrinketsStacks(LivingEntity living)
	{
		List<StorageSlotContext> list = new ArrayList<>();

		if (CompatibleManagerDelegate.TRINKETS.isLoaded())
		{
			Container rawContainer = TrinketsHelper.getEquippedTrinkets(living);

			if (rawContainer != null)
			{
				WrappedVanillaContainer container = new WrappedVanillaContainer(rawContainer);

				for (int i = 0; i < rawContainer.getContainerSize(); i++)
				{
					ItemStack item = rawContainer.getItem(i);

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
