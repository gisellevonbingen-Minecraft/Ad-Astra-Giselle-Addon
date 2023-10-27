package ad_astra_giselle_addon.common.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.item.ItemStackConsumers;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class LivingHelper
{
	private static final LivingHelper.Delegate DELEGATE = new LivingHelperDelegate();

	public static boolean isPlayingMode(LivingEntity living)
	{
		if (living instanceof Player player)
		{
			return !player.isCreative() && !player.isSpectator();
		}
		else
		{
			return true;
		}

	}

	public static List<ItemStackReference> getSlotItems(LivingEntity living)
	{
		List<ItemStackReference> list = new ArrayList<>();

		for (InteractionHand hand : InteractionHand.values())
		{
			list.add(getHandItem(living, hand));
		}

		list.addAll(getEquipmentItems(living));
		list.addAll(DELEGATE.getExtraSlotItems(living));
		return list;
	}

	public static ItemStackReference getHandItem(LivingEntity living, InteractionHand hand)
	{
		return new ItemStackReference(living.getItemInHand(hand), ItemStackConsumers.hand(hand, living::setItemInHand));
	}

	public static List<ItemStackReference> getEquipmentItems(LivingEntity living)
	{
		List<ItemStackReference> list = new ArrayList<>();

		for (EquipmentSlot slot : EquipmentSlot.values())
		{
			list.add(getEquipmentItem(living, slot));
		}

		return list;
	}

	public static ItemStackReference getEquipmentItem(LivingEntity living, EquipmentSlot slot)
	{
		return new ItemStackReference(living.getItemBySlot(slot), ItemStackConsumers.equipment(slot, living::setItemSlot));
	}

	public static List<ItemStackReference> getInventoryItems(LivingEntity living)
	{
		List<ItemStackReference> list = new ArrayList<>();

		if (living instanceof Player player)
		{
			Inventory inventory = player.getInventory();
			int size = inventory.getContainerSize();

			for (int i = 0; i < size; i++)
			{
				ItemStack item = inventory.getItem(i);

				if (!item.isEmpty())
				{
					list.add(getInventoryitem(inventory, i));
				}

			}

			list.addAll(DELEGATE.getExtraSlotItems(living));
		}
		else
		{
			list.addAll(getSlotItems(living));
		}

		return list;
	}

	public static ItemStackReference getInventoryitem(Inventory inventory, int i)
	{
		return new ItemStackReference(inventory.getItem(i), ItemStackConsumers.index(i, inventory::setItem));
	}

	public static interface Delegate
	{
		default List<ItemStackReference> getExtraSlotItems(LivingEntity living)
		{
			return Collections.emptyList();
		}

	}

}
