package ad_astra_giselle_addon.client;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import ad_astra_giselle_addon.common.config.AddonConfigs;
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

	private static boolean tooltipEnabeldCached = false;
	private static boolean tooltipEnabled = false;

	public static boolean tooltipEnabled()
	{
		if (tooltipEnabeldCached)
		{
			return tooltipEnabled;
		}
		else
		{
			tooltipEnabeldCached = true;
			EnchantmentsConfig config = AddonConfigs.Common.enchantments;
			return tooltipEnabled = config.tooltip_Enabled.get() && (config.tooltip_Ignore.get() || !isDescriptionModsLoaded());
		}

	}

	public static void addDescriptionMod(String modid)
	{
		if (DESCRIPTION_MODS.add(modid))
		{
			tooltipEnabled = tooltipEnabled() && !ModList.get().isLoaded(modid);
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
