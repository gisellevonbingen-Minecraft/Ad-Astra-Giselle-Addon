package ad_astra_giselle_addon.client;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public interface ItemTooltipModifier
{
	void getTooltip(ItemStack item, Item.TooltipContext context, TooltipFlag flags, List<Component> lines);
}
