package ad_astra_giselle_addon.client;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.enchantment.EnchantmentHelper2;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class EventListenerEnchantmentTooltip
{
	private static final Set<String> DESCRIPTION_MODS = Sets.newHashSet("enchdesc", "cofh_core");

	private static boolean TOOLTIP_ENABELD_CACHED = false;
	private static boolean TOOLTIP_ENABLED = false;

	public static boolean tooltipEnabled()
	{
		if (TOOLTIP_ENABELD_CACHED)
		{
			return TOOLTIP_ENABLED;
		}
		else
		{
			TOOLTIP_ENABELD_CACHED = true;
			return TOOLTIP_ENABLED = EnchantmentsConfig.TOOLTIP_ENABLED && (EnchantmentsConfig.TOOLTIP_IGNORE || !isDescriptionModsLoaded());
		}

	}

	public static void addDescriptionMod(String modid)
	{
		if (DESCRIPTION_MODS.add(modid))
		{
			TOOLTIP_ENABLED = tooltipEnabled() && !ModList.get().isLoaded(modid);
		}

	}

	public static boolean isDescriptionModsLoaded()
	{
		for (String mod : DESCRIPTION_MODS)
		{
			if (ModList.get().isLoaded(mod))
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

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent e)
	{
		ItemStack itemstack = e.getItemStack();

		if (itemstack.getItem() instanceof EnchantedBookItem)
		{
			if (!tooltipEnabled())
			{
				return;
			}

			List<Component> lines = e.getToolTip();

			for (Enchantment enchantment : AddonEnchantments.ENCHANTMENTS.getObjects())
			{
				for (Component line : lines)
				{
					if (line.getContents() instanceof TranslatableContents contents)
					{
						if (contents.getKey().equals(enchantment.getDescriptionId()))
						{
							lines.add(lines.indexOf(line) + 1, EnchantmentHelper2.getDescriptionText(enchantment));
							break;
						}

					}

				}

			}

		}

	}

}
