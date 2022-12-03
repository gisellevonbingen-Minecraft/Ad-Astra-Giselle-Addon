package ad_astra_giselle_addon.common.registries;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.delegate.CreativeModeTabBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class AddonTabs
{
	public static final CreativeModeTab tab_main = AdAstraGiselleAddon.delegate().createCreativeModeTab(//
			new CreativeModeTabBuilder(AdAstraGiselleAddon.rl("tab_main")).icon(() -> new ItemStack(AddonBlocks.FUEL_LOADER)).fillItems(() ->
			{
				NonNullList<ItemStack> list = NonNullList.create();

				for (Item item : AdAstraGiselleAddon.delegate().getRegistryHelper().getRegistry(Registry.ITEM_REGISTRY).getValues())
				{
					item.fillItemCategory(AddonTabs.tab_main, list);
				}

				for (DelegateObjectHolder<Enchantment> registryObject : AddonEnchantments.ENCHANTMENTS.getObjects())
				{
					Enchantment enchantment = registryObject.get();

					for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++)
					{
						ItemStack enchantedBook = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i));
						list.add(enchantedBook);
					}

				}

				return list;
			}));

	public static void visit()
	{

	}
}
