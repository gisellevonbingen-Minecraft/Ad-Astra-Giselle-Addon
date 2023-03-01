package ad_astra_giselle_addon.common.registry;

import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.item.ICreativeTabOutputProvider;
import ad_astra_giselle_addon.common.util.ModHooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class AddonTabs
{
	public static void register(BiConsumer<String, Consumer<CreativeModeTab.Builder>> register)
	{
		register.accept("tab_main", builder ->
		{
			builder.title(Component.literal(ModHooks.getName(AdAstraGiselleAddon.MOD_ID)));
			builder.icon(() -> new ItemStack(AddonBlocks.FUEL_LOADER));
			builder.displayItems((FeatureFlagSet pEnabledFeatures, CreativeModeTab.Output pOutput, boolean pDisplayOperatorCreativeTab) ->
			{
				for (Entry<ResourceKey<Item>, Item> entry : ObjectRegistry.get(Registries.ITEM).getEntries())
				{
					if (entry.getKey().location().getNamespace().equals(AdAstraGiselleAddon.MOD_ID))
					{
						Item item = entry.getValue();
						pOutput.accept(item);

						if (item instanceof ICreativeTabOutputProvider provider)
						{
							provider.provideCreativeTabOutput(pOutput);
						}

					}

				}

				for (Enchantment enchantment : AddonEnchantments.ENCHANTMENTS.getValues())
				{
					for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++)
					{
						ItemStack enchantedBook = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i));
						pOutput.accept(enchantedBook);
					}

				}

			});

		});

	}

}
