package ad_astra_giselle_addon.common.compat.techreborn;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.command.AddonCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;

public class TechRebornCommand
{
	public static int quantum_armor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		CommandSourceStack source = context.getSource();
		ServerPlayer player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlot.HEAD, AddonCommand.Equip.makeFullWithEnchantments(TechRebornCompat.rl("quantum_helmet")));
		player.setItemSlot(EquipmentSlot.CHEST, AddonCommand.Equip.makeFullWithEnchantments(TechRebornCompat.rl("quantum_chestplate")));
		player.setItemSlot(EquipmentSlot.LEGS, AddonCommand.Equip.makeFullWithEnchantments(TechRebornCompat.rl("quantum_leggings")));
		player.setItemSlot(EquipmentSlot.FEET, AddonCommand.Equip.makeFullWithEnchantments(TechRebornCompat.rl("quantum_boots")));

		return AddonCommand.sendEquipedMessage(source);
	}

	private TechRebornCommand()
	{

	}

}
