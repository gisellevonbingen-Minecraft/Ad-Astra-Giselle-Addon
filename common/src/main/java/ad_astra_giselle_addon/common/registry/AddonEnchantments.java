package ad_astra_giselle_addon.common.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class AddonEnchantments
{
	private static final List<ResourceKey<Enchantment>> _ENCHANTMENTS = new ArrayList<>();
	public static final List<ResourceKey<Enchantment>> ENCHANTMENTS = Collections.unmodifiableList(_ENCHANTMENTS);

	public static final ResourceKey<Enchantment> OXYGEN_PROOF = key("space_breathing");
	public static final ResourceKey<Enchantment> HOT_TEMPERATURE_PROOF = key("space_fire_proof");
	public static final ResourceKey<Enchantment> ACID_RAIN_PROOF = key("acid_rain_proof");
	public static final ResourceKey<Enchantment> GRAVITY_PROOF = key("gravity_normalizing");

	public static void bootstrap(BootstrapContext<Enchantment> context)
	{
		HolderGetter<Item> itemHolderGetter = context.lookup(Registries.ITEM);
		register(context, OXYGEN_PROOF, Enchantment.enchantment(Enchantment.definition(//
				itemHolderGetter.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 10, 1, //
				Enchantment.constantCost(0), Enchantment.constantCost(0), 1, EquipmentSlotGroup.HEAD)));
		register(context, HOT_TEMPERATURE_PROOF, Enchantment.enchantment(Enchantment.definition(//
				itemHolderGetter.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 10, 1, //
				Enchantment.constantCost(0), Enchantment.constantCost(0), 1, EquipmentSlotGroup.CHEST)));
		register(context, ACID_RAIN_PROOF, Enchantment.enchantment(Enchantment.definition(//
				itemHolderGetter.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 10, 1, //
				Enchantment.constantCost(0), Enchantment.constantCost(0), 1, EquipmentSlotGroup.CHEST)));
		register(context, GRAVITY_PROOF, Enchantment.enchantment(Enchantment.definition(//
				itemHolderGetter.getOrThrow(ItemTags.ARMOR_ENCHANTABLE), 10, 1, //
				Enchantment.constantCost(0), Enchantment.constantCost(0), 1, EquipmentSlotGroup.FEET)));
	}

	private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder)
	{
		context.register(key, builder.build(key.location()));
	}

	private static ResourceKey<Enchantment> key(String name)
	{
		ResourceKey<Enchantment> key = ResourceKey.create(Registries.ENCHANTMENT, AdAstraGiselleAddon.rl(name));
		_ENCHANTMENTS.add(key);
		return key;
	}

	private AddonEnchantments()
	{

	}

}
