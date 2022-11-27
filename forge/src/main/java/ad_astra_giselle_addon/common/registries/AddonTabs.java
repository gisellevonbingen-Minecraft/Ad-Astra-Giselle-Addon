package ad_astra_giselle_addon.common.registries;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.RegistryObject;

public class AddonTabs
{
	public static final CreativeModeTab tab_main = new CreativeModeTab(AdAstraGiselleAddon.MOD_ID + "_tab_main")
	{
		@Override
		public void fillItemList(NonNullList<ItemStack> list)
		{
			super.fillItemList(list);

			for (RegistryObject<Enchantment> registryObject : AddonEnchantments.ENCHANTMENTS.getEntries())
			{
				Enchantment enchantment = registryObject.get();

				for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++)
				{
					ItemStack enchantedBook = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i));
					list.add(enchantedBook);
				}

			}

		}

		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(AddonBlocks.FUEL_LOADER);
		}

	};

}
