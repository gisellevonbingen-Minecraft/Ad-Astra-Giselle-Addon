package ad_astra_giselle_addon.common.util;

import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
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
			list.add(new ItemStackReference(living.getItemInHand(hand), ItemStackConsumers.hand(hand, living::setItemInHand)));
		}

		list.addAll(getArmorItems(living));
		return list;
	}

	public static List<ItemStackReference> getArmorItems(LivingEntity living)
	{
		List<ItemStackReference> list = new ArrayList<>();

		for (EquipmentSlot slot : EquipmentSlot.values())
		{
			list.add(new ItemStackReference(living.getItemBySlot(slot), ItemStackConsumers.equipment(slot, living::setItemSlot)));
		}

		return list;
	}

	public static List<ItemStackReference> getInventoryStacks(LivingEntity living)
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
					list.add(new ItemStackReference(item, ItemStackConsumers.index(i, inventory::setItem)));
				}

			}

		}
		else
		{
			list.addAll(getSlotItems(living));
		}

		list.addAll(AdAstraGiselleAddon.delegate().getLivingHelper().getExtraInventoryStacks(living));

		return list;
	}

	private LivingHelper()
	{

	}

}
