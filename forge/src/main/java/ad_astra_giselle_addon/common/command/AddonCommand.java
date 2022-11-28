package ad_astra_giselle_addon.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.compat.mekanism.MekanismCommand;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.PneumaticCraftCommand;
import ad_astra_giselle_addon.common.compat.redstonearsenal.RSACommand;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import earth.terrarium.ad_astra.registry.ModFluids;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.energy.EnergyItem;
import earth.terrarium.botarium.api.energy.StatefulEnergyContainer;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

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
		return cs.hasPermission(2) && cs.getEntity() instanceof ServerPlayer;
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

			if (CompatibleManager.MEKANISM.isLoaded())
			{
				builder.then(Commands.literal("mekasuit").executes(MekanismCommand::mekasuit));
			}

			if (CompatibleManager.PNEUMATICCRAFT.isLoaded())
			{
				builder.then(Commands.literal("pneumatic_armor").executes(PneumaticCraftCommand::pneumatic_armor));
			}

			if (CompatibleManager.REDSTONE_ARSENAL.isLoaded())
			{
				builder.then(Commands.literal("flux_armor").executes(RSACommand::flux_armor));
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

			player.setItemSlot(EquipmentSlot.HEAD, makeFull(Items.DIAMOND_HELMET, AddonEnchantments.SPACE_BREATHING.get()));
			player.setItemSlot(EquipmentSlot.CHEST, makeFull(Items.DIAMOND_CHESTPLATE, AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
			player.setItemSlot(EquipmentSlot.LEGS, makeFull(Items.DIAMOND_LEGGINGS));
			player.setItemSlot(EquipmentSlot.FEET, makeFull(Items.DIAMOND_BOOTS));

			return sendEquipedMessage(source);
		}

		public static ItemStack makeFull(ResourceLocation name, Enchantment... enchantments)
		{
			Item item = ForgeRegistries.ITEMS.getValue(name);
			return makeFull(item, enchantments);
		}

		private static ItemStack makeFull(Item item, Enchantment... enchantments)
		{
			ItemStackHolder holder = new ItemStackHolder(new ItemStack(item));

			for (Enchantment enchantment : enchantments)
			{
				holder.getStack().enchant(enchantment, 1);
			}

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
					for (int i = 0; i < 100; i++)
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
