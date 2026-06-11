package ad_astra_giselle_addon.common.enchantment;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnchantmentHelper2
{
	private static final Map<Holder<Enchantment>, String> DESCRIPTION_KEYS = new HashMap<>();
	private static final Map<Holder<Enchantment>, Component> DESCRIPTION_TEXTS = new HashMap<>();
	private static final Map<Holder<Enchantment>, List<? extends Component>> DESCRIPTION_TEXTS_MULTILINES = new HashMap<>();
	private static final List<String> DESCRIPTION_SUFFIXES = Lists.newArrayList("desc", "description");

	public static List<String> getDescriptionSuffixes()
	{
		return Collections.unmodifiableList(DESCRIPTION_SUFFIXES);
	}

	public static boolean addDescriptionSuffix(String suffix)
	{
		return DESCRIPTION_SUFFIXES.add(suffix);
	}

	public static String getDescriptionKey(Holder<Enchantment> enchantment)
	{
		return DESCRIPTION_KEYS.computeIfAbsent(enchantment, e ->
		{
			String descriptionId;

			if (e.value().description().getContents() instanceof TranslatableContents contents)
			{
				descriptionId = contents.getKey();
			}
			else
			{
				descriptionId = Util.makeDescriptionId("enchantment", e.getKey().location());
			}

			for (String suffix : DESCRIPTION_SUFFIXES)
			{
				String key = descriptionId + "." + suffix;

				if (I18n.exists(key))
				{
					return key;
				}

			}

			return descriptionId + DESCRIPTION_SUFFIXES.get(0);
		});

	}

	public static Component getDescriptionText(Holder<Enchantment> enchantment)
	{
		return DESCRIPTION_TEXTS.computeIfAbsent(enchantment, e ->
		{
			return Component.translatable(getDescriptionKey(enchantment)).withStyle(ChatFormatting.GOLD);
		});
	}

	public static void clearDescriptionsCache()
	{
		DESCRIPTION_TEXTS_MULTILINES.clear();
	}

	public static List<? extends Component> getDescriptionTexts(Holder<Enchantment> enchantment)
	{
		return DESCRIPTION_TEXTS_MULTILINES.computeIfAbsent(enchantment, e ->
		{
			return Arrays.stream(Component.translatable(getDescriptionKey(enchantment)).getString().split("\n")).map(Component::literal).map(s -> s.withStyle(ChatFormatting.GOLD)).toList();
		});
	}

	@NotNull
	public static Pair<EquipmentSlot, Integer> getEnchantmentItemAndLevel(Holder<Enchantment> enchantment, LivingEntity living)
	{
		Map<EquipmentSlot, ItemStack> map = enchantment.value().getSlotItems(living);
		EquipmentSlot slot = null;
		int level = 0;

		for (Entry<EquipmentSlot, ItemStack> pair : map.entrySet())
		{
			int i = EnchantmentHelper.getItemEnchantmentLevel(enchantment, pair.getValue());

			if (i > level)
			{
				slot = pair.getKey();
				level = i;
			}

		}

		if (slot != null)
		{
			return Pair.of(slot, level);
		}
		else
		{
			return Pair.of(null, level);
		}

	}

	private EnchantmentHelper2()
	{

	}

}
