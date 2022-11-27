package ad_astra_giselle_addon.common.item;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ItemStackConsumers
{
	public static Consumer<ItemStack> hand(InteractionHand hand, BiConsumer<InteractionHand, ItemStack> consumer)
	{
		return item -> consumer.accept(hand, item);
	}

	public static Consumer<ItemStack> equipment(EquipmentSlot slot, BiConsumer<EquipmentSlot, ItemStack> consumer)
	{
		return item -> consumer.accept(slot, item);
	}

	public static Consumer<ItemStack> index(int index, BiConsumer<Integer, ItemStack> consumer)
	{
		return item -> consumer.accept(index, item);
	}

	private ItemStackConsumers()
	{

	}

}
