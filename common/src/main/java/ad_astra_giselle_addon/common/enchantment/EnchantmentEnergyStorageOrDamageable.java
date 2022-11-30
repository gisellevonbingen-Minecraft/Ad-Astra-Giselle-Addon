package ad_astra_giselle_addon.common.enchantment;

import ad_astra_giselle_addon.common.item.ItemUsableResource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentEnergyStorageOrDamageable extends Enchantment
{
	public EnchantmentEnergyStorageOrDamageable(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots)
	{
		super(rarity, category, slots);
	}

	@Override
	public boolean isTradeable()
	{
		return false;
	}

	@Override
	public boolean isDiscoverable()
	{
		return false;
	}

	@Override
	public boolean canEnchant(ItemStack stack)
	{
		return ItemUsableResource.first(stack) != null;
	}

	@Override
	public int getMinCost(int level)
	{
		return 1;
	}

	@Override
	public int getMaxCost(int level)
	{
		return this.getMinCost(level);
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}

}
