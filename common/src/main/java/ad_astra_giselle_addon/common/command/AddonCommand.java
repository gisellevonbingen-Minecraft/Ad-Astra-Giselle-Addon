package ad_astra_giselle_addon.common.command;

import java.util.ArrayList;
import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.compat.CompatibleMod;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import ad_astra_giselle_addon.common.registry.ObjectRegistry;
import earth.terrarium.ad_astra.common.registry.ModFluids;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.EnergyItem;
import earth.terrarium.botarium.api.energy.StatefulEnergyContainer;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
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
		source.sendSuccess(Component.literal("Equipped"), false);
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

			for (CompatibleMod mod : AdAstraGiselleAddon.compats().all_mods)
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

			player.setItemSlot(EquipmentSlot.HEAD, makeFullWithEnchantments(Items.DIAMOND_HELMET));
			player.setItemSlot(EquipmentSlot.CHEST, makeFullWithEnchantments(Items.DIAMOND_CHESTPLATE));
			player.setItemSlot(EquipmentSlot.LEGS, makeFullWithEnchantments(Items.DIAMOND_LEGGINGS));
			player.setItemSlot(EquipmentSlot.FEET, makeFullWithEnchantments(Items.DIAMOND_BOOTS));

			return sendEquipedMessage(source);
		}

		public static ItemStack makeFullWithEnchantments(ResourceLocation name)
		{
			Item item = ObjectRegistry.get(Registry.ITEM_REGISTRY).getValue(name);
			return makeFullWithEnchantments(item);
		}

		private static ItemStack makeFullWithEnchantments(Item item)
		{
			ItemStack stack = makeFull(item);

			for (Enchantment enchantment : AddonEnchantments.ENCHANTMENTS.getValues())
			{
				if (enchantment.canEnchant(stack))
				{
					stack.enchant(enchantment, 1);
				}

			}

			return stack;
		}

		private static ItemStack makeFull(Item item)
		{
			ItemStackHolder holder = new ItemStackHolder(new ItemStack(item));

			if (holder.getStack().getItem() instanceof EnergyItem energyItem)
			{
				StatefulEnergyContainer<ItemStack> energyStorage = energyItem.getEnergyStorage(holder.getStack());
				energyStorage.setEnergy(energyStorage.getMaxCapacity());
				energyStorage.update(holder.getStack());
			}
			else
			{
				EnergyHooks.safeGetItemEnergyManager(holder.getStack()).ifPresent(energyHandler ->
				{
					for (int i = 0; i < 100000; i++)
					{
						if (energyHandler.insert(holder, energyHandler.getCapacity(), false) == 0)
						{
							break;
						}

					}

				});

			}

			UniveralFluidHandler.fromSafe(holder).ifPresent(fluidHandler ->
			{
				fluidHandler.insertFluid(FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), Integer.MAX_VALUE, null), false);
			});

			return holder.getStack();
		}

	}

}
