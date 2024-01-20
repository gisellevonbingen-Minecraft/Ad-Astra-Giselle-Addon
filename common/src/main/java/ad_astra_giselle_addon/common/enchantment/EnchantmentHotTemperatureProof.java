package ad_astra_giselle_addon.common.enchantment;

import earth.terrarium.adastra.common.items.armor.NetheriteSpaceSuitItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentHotTemperatureProof extends AddonEnchantment
{
	public EnchantmentHotTemperatureProof()
	{
		super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.CHEST);
	}

	@Override
	public boolean canEnchant(ItemStack stack)
	{
		if (stack.getItem() instanceof NetheriteSpaceSuitItem)
		{
			return false;
		}

		return super.canEnchant(stack);
	}

}
