package ad_astra_giselle_addon.common.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.item.StorageSlotContext;
import earth.terrarium.common_storage_lib.context.impl.PlayerContext;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

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

	// TODO: Support LivingEntity
	public static List<StorageSlotContext> getSlots(Player living)
	{
		List<StorageSlotContext> list = new ArrayList<>();
		list.addAll(getEquipmentSlots(living));
		list.addAll(DELEGATE.getExtraEquipmentSlots(living));
		return list;
	}

	// TODO: Support LivingEntity
	public static List<StorageSlotContext> getEquipmentSlots(Player living)
	{
		List<StorageSlotContext> list = new ArrayList<>();

		// TODO : Fix
		for (EquipmentSlot equipmentSlot : EquipmentSlot.values())
		{
			StorageSlotContext itemSlot = getEquipmentSlot(living, equipmentSlot);

			if (itemSlot != null)
			{
				list.add(itemSlot);
			}

		}

		return list;
	}

	@Nullable
	public static StorageSlotContext getEquipmentSlot(Player living, EquipmentSlot slot)
	{
		if (slot.getType() == EquipmentSlot.Type.HAND)
		{
			if (slot == EquipmentSlot.MAINHAND)
			{
				return StorageSlotContext.ofContext(PlayerContext.ofHand(living, InteractionHand.MAIN_HAND));
			}
			else if (slot == EquipmentSlot.OFFHAND)
			{
				return StorageSlotContext.ofContext(PlayerContext.ofHand(living, InteractionHand.OFF_HAND));
			}

		}
		else if (slot.getType() == Type.HUMANOID_ARMOR)
		{
			if (slot == EquipmentSlot.FEET || slot == EquipmentSlot.LEGS || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.HEAD)
			{
				return StorageSlotContext.ofContext(PlayerContext.ofSlot(living, Inventory.INVENTORY_SIZE + slot.getIndex()));
			}

		}

		return null;
	}

	// TODO : Support LivingEntity
	public static List<StorageSlotContext> getInventorySlots(Player living)
	{
		List<StorageSlotContext> list = new ArrayList<>();

		for (int i = 0; i < living.getInventory().getContainerSize(); i++)
		{
			list.add(StorageSlotContext.ofContext(getInventorySlot(living, i)));
		}

		list.addAll(DELEGATE.getExtraEquipmentSlots(living));
		return list;
	}

	public static StorageSlotContext getInventorySlot(Player living, int slot)
	{
		return StorageSlotContext.ofContext(PlayerContext.ofSlot(living, slot));
	}

	public static interface Delegate
	{
		default List<StorageSlotContext> getExtraEquipmentSlots(LivingEntity living)
		{
			return Collections.emptyList();
		}

	}

}
