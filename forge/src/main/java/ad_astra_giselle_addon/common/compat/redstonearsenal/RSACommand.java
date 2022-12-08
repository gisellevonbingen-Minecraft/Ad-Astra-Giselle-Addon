package ad_astra_giselle_addon.common.compat.redstonearsenal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.command.AddonCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;

public class RSACommand
{
	public static int flux_armor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		CommandSourceStack source = context.getSource();
		ServerPlayer player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlot.HEAD, AddonCommand.Equip.makeFullWithEnchantments(RedstoneArsenalCompat.rl("flux_helmet")));
		player.setItemSlot(EquipmentSlot.CHEST, AddonCommand.Equip.makeFullWithEnchantments(RedstoneArsenalCompat.rl("flux_chestplate")));
		player.setItemSlot(EquipmentSlot.LEGS, AddonCommand.Equip.makeFullWithEnchantments(RedstoneArsenalCompat.rl("flux_leggings")));
		player.setItemSlot(EquipmentSlot.FEET, AddonCommand.Equip.makeFullWithEnchantments(RedstoneArsenalCompat.rl("flux_boots")));

		return AddonCommand.sendEquipedMessage(source);
	}

	private RSACommand()
	{

	}

}
