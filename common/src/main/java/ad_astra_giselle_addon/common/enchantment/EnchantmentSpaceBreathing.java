package ad_astra_giselle_addon.common.enchantment;

import earth.terrarium.ad_astra.common.item.armor.SpaceSuit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentSpaceBreathing extends AddonEnchantment
{
	public EnchantmentSpaceBreathing()
	{
		super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD);
	}

	@Override
	public boolean canEnchant(ItemStack stack)
	{
		if (stack.getItem() instanceof SpaceSuit)
		{
			return false;
		}

		return super.canEnchant(stack);
	}

}
