package ad_astra_giselle_addon.common.entity;

import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.common.compat.CompatibleManagerDelegate;
import ad_astra_giselle_addon.common.compat.trinkets.TrinketsHelper;
import ad_astra_giselle_addon.common.item.ItemStackConsumers;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class LivingHelperDelegate implements LivingHelper.Delegate
{
	@Override
	public List<ItemStackReference> getExtraSlotEquipments(LivingEntity living)
	{
		List<ItemStackReference> list = new ArrayList<>();
		list.addAll(this.getTrinketsStacks(living));
		return list;
	}

	public List<ItemStackReference> getTrinketsStacks(LivingEntity living)
	{
		List<ItemStackReference> list = new ArrayList<>();

		if (CompatibleManagerDelegate.TRINKETS.isLoaded())
		{
			Container container = TrinketsHelper.getEquippedTrinkets(living);

			if (container != null)
			{
				for (int i = 0; i < container.getContainerSize(); i++)
				{
					ItemStack item = container.getItem(i);

					if (!item.isEmpty())
					{
						list.add(new ItemStackReference(item, ItemStackConsumers.index(i, container::setItem)));
					}

				}

			}

		}

		return list;
	}

}
