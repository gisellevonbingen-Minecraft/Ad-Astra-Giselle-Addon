package ad_astra_giselle_addon.client;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import ad_astra_giselle_addon.common.config.EnchantmentsConfig;
import ad_astra_giselle_addon.common.enchantment.EnchantmentHelper2;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import ad_astra_giselle_addon.common.util.ModHooks;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.EnchantedBookItem;
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
			if (ModHooks.isLoaded(mod))
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

	public static void addTooltip(ItemTooltipModifier modifier)
	{
		if (modifier.item().getItem() instanceof EnchantedBookItem && tooltipEnabled())
		{
			List<Component> lines = modifier.lines();
			RegistryLookup<Enchantment> lookupOrThrow = AdAstra.getRegistryAccess().lookupOrThrow(Registries.ENCHANTMENT);

			for (ResourceKey<Enchantment> key : AddonEnchantments.ENCHANTMENTS)
			{
				Reference<Enchantment> holder = lookupOrThrow.getOrThrow(key);

				if (holder.value().description().getContents() instanceof TranslatableContents contents1)
				{
					for (Component line : lines)
					{
						if (line.getContents() instanceof TranslatableContents contents2)
						{
							if (contents2.getKey().equals(contents1.getKey()))
							{
								modifier.lines().addAll(modifier.lines().indexOf(line) + 1, EnchantmentHelper2.getDescriptionTexts(holder));
								break;
							}

						}

					}

					if (modifier.item().getEnchantmentLevel(holder) > 0)
					{
						modifier.lines().addAll(EnchantmentHelper2.getDescriptionTexts(holder));
					}

				}

			}

		}

	}

}
