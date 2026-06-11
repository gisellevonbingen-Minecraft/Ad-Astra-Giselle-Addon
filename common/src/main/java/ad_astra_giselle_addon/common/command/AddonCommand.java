package ad_astra_giselle_addon.common.command;

import java.util.ArrayList;
import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.compat.CompatibleMod;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import ad_astra_giselle_addon.common.registry.ObjectRegistry;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public class AddonCommand
{
	private AddonCommand()
	{

	}

	public static LiteralArgumentBuilder<CommandSourceStack> builder()
	{
		return Commands.literal("giselle_addon") //
				.then(Equip.builder()) //
		;
	}

	public static boolean isPlayerHasPermission(CommandSourceStack cs, int permission)
	{
		return cs.hasPermission(permission) && cs.getEntity() instanceof ServerPlayer;
	}

	public static boolean isPlayerHasPermission2(CommandSourceStack cs)
	{
		return isPlayerHasPermission(cs, 2);
	}

	public static int sendEquipedMessage(CommandSourceStack source)
	{
		source.sendSuccess(() -> Component.literal("Equipped"), false);
		return 0;
	}

	public static class Equip
	{
		public static LiteralArgumentBuilder<CommandSourceStack> builder()
		{
			LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("equip").requires(AddonCommand::isPlayerHasPermission2) //
					.then(Commands.literal("space_suit").executes(Equip::space_suit)) //
					.then(Commands.literal("netherite_space_suit").executes(Equip::netherite_space_suit)) //
					.then(Commands.literal("jet_suit").executes(Equip::jet_suit)) //
					.then(Commands.literal("diamond").executes(Equip::diamond)) //
			;

			for (CompatibleMod mod : AdAstraGiselleAddon.compats().loaded_mods)
			{
				List<ArgumentBuilder<CommandSourceStack, ?>> list = new ArrayList<>();
				mod.collectEquipCommands(list);

				for (ArgumentBuilder<CommandSourceStack, ?> command : list)
				{
					builder.then(command);
				}

			}

			return builder;
		}

		public static int space_suit(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlot.HEAD, makeFull(ModItems.SPACE_HELMET.get()));
			player.setItemSlot(EquipmentSlot.CHEST, makeFull(ModItems.SPACE_SUIT.get()));
			player.setItemSlot(EquipmentSlot.LEGS, makeFull(ModItems.SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlot.FEET, makeFull(ModItems.SPACE_BOOTS.get()));

			return sendEquipedMessage(source);
		}

		public static int netherite_space_suit(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlot.HEAD, makeFull(ModItems.NETHERITE_SPACE_HELMET.get()));
			player.setItemSlot(EquipmentSlot.CHEST, makeFull(ModItems.NETHERITE_SPACE_SUIT.get()));
			player.setItemSlot(EquipmentSlot.LEGS, makeFull(ModItems.NETHERITE_SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlot.FEET, makeFull(ModItems.NETHERITE_SPACE_BOOTS.get()));

			return sendEquipedMessage(source);
		}

		public static int jet_suit(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlot.HEAD, makeFull(ModItems.JET_SUIT_HELMET.get()));
			player.setItemSlot(EquipmentSlot.CHEST, makeFull(ModItems.JET_SUIT.get()));
			player.setItemSlot(EquipmentSlot.LEGS, makeFull(ModItems.JET_SUIT_PANTS.get()));
			player.setItemSlot(EquipmentSlot.FEET, makeFull(ModItems.JET_SUIT_BOOTS.get()));

			return sendEquipedMessage(source);
		}

		public static int diamond(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();
			RegistryAccess registryAccess = player.registryAccess();

			player.setItemSlot(EquipmentSlot.HEAD, makeFullWithEnchantments(Items.DIAMOND_HELMET, registryAccess));
			player.setItemSlot(EquipmentSlot.CHEST, makeFullWithEnchantments(Items.DIAMOND_CHESTPLATE, registryAccess));
			player.setItemSlot(EquipmentSlot.LEGS, makeFullWithEnchantments(Items.DIAMOND_LEGGINGS, registryAccess));
			player.setItemSlot(EquipmentSlot.FEET, makeFullWithEnchantments(Items.DIAMOND_BOOTS, registryAccess));

			return sendEquipedMessage(source);
		}

		public static ItemStack makeFullWithEnchantments(ResourceLocation name, HolderLookup.Provider provider)
		{
			Item item = ObjectRegistry.get(Registries.ITEM).getValue(name);
			return makeFullWithEnchantments(item, provider);
		}

		private static ItemStack makeFullWithEnchantments(Item item, HolderLookup.Provider provider)
		{
			ItemStack stack = makeFull(item);
			HolderGetter<Enchantment> enchantmentLookup = provider.asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT);

			for (ResourceKey<Enchantment> key : AddonEnchantments.ENCHANTMENTS)
			{
				Reference<Enchantment> enchantment = enchantmentLookup.getOrThrow(key);

				if (stack.supportsEnchantment(enchantment))
				{
					stack.enchant(enchantment, 1);
				}

			}

			return stack;
		}

		private static ItemStack makeFull(Item item)
		{
			StorageSlotContext context = StorageSlotContext.ofIsolated(new ItemStack(item));
			ValueStorage energyContainer = context.find(EnergyApi.ITEM);
			CommonStorage<FluidResource> fluidContainer = context.find(FluidApi.ITEM);

			if (energyContainer != null)
			{
				for (int i = 0; i < 100000; i++)
				{
					if (energyContainer.insert(energyContainer.getCapacity(), false) == 0)
					{
						break;
					}

				}

			}

			if (fluidContainer != null)
			{
				fluidContainer.insert(FluidResource.of(ModFluids.OXYGEN.get()), Integer.MAX_VALUE, false);
			}

			return context.getItemStack();
		}

	}

}
