package ad_astra_giselle_addon.client;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.enchantment.EnchantmentHelper2;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import earth.terrarium.botarium.util.CommonHooks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantedBookTooltipHelper
{
	private static final Set<String> DESCRIPTION_MODS = Sets.newHashSet("enchdesc", "enchantment_lore", "cofh_core");

	public static boolean tooltipEnabled()
	{
		return EnchantmentsConfig.TOOLTIP_ENABLED && (EnchantmentsConfig.TOOLTIP_IGNORE || !isDescriptionModsLoaded());
	}

	public static void addDescriptionMod(String modid)
	{
		DESCRIPTION_MODS.add(modid);
	}

	public static boolean isDescriptionModsLoaded()
	{
		for (String mod : DESCRIPTION_MODS)
		{
			if (CommonHooks.isModLoaded(mod))
			{
				return true;
			}

		}

		return false;
	}

	public static Set<String> getDescriptionMods()
	{
		return Collections.unmodifiableSet(DESCRIPTION_MODS);
	}

	public static void addTooltip(ItemStack item, TooltipFlag flags, List<Component> lines)
	{
		if (item.getItem() instanceof EnchantedBookItem && tooltipEnabled())
		{
			for (Enchantment enchantment : AddonEnchantments.ENCHANTMENTS.getValues())
			{
				for (Component line : lines)
				{
					if (line.getContents() instanceof TranslatableContents contents)
					{
						if (contents.getKey().equals(enchantment.getDescriptionId()))
						{
							lines.addAll(lines.indexOf(line) + 1, EnchantmentHelper2.getDescriptionTexts(enchantment));
							break;
						}

					}

				}

			}

		}

	}

}
