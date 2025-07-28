package ad_astra_giselle_addon.common.compat.tconstruct;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.command.AddonCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.item.ArmorSlotType;

public class TConstructCommand
{
	public static int tconstruct(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		CommandSourceStack source = context.getSource();
		ServerPlayer player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlot.HEAD, createWithModifiers(ArmorSlotType.HELMET, AddonTConstructModifiers.OXYGEN_PROOF.get()));
		player.setItemSlot(EquipmentSlot.CHEST, createWithModifiers(ArmorSlotType.CHESTPLATE, AddonTConstructModifiers.HOT_TEMPERATURE_PROOF.get(), AddonTConstructModifiers.ACID_RAIN_PROOF.get()));
		player.setItemSlot(EquipmentSlot.LEGS, createWithModifiers(ArmorSlotType.LEGGINGS));
		player.setItemSlot(EquipmentSlot.FEET, createWithModifiers(ArmorSlotType.BOOTS, AddonTConstructModifiers.GRAVITY_PROOF.get()));

		return AddonCommand.sendEquipedMessage(source);
	}

	public static ItemStack createWithModifiers(ArmorSlotType slot, Modifier... modifiers)
	{
		var item = new ItemStack(TinkerTools.travelersGear.get(slot));
		var tool = ToolStack.from(item);

		for (Modifier modifier : modifiers)
		{
			tool.addModifier(modifier.getId(), 1);
		}

		return item;
	}

	private TConstructCommand()
	{

	}

}
