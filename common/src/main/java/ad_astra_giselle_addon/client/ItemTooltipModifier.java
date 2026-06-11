package ad_astra_giselle_addon.client;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public record ItemTooltipModifier(ItemStack item, TooltipFlag flags, List<Component> lines, Item.TooltipContext context)
{

}
