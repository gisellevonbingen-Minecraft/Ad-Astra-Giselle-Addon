package ad_astra_giselle_addon.common.compat.atm;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.command.AddonCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;

public class ATMCommand
{
	public static int allthemodium(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		CommandSourceStack source = context.getSource();
		ServerPlayer player = source.getPlayerOrException();
		RegistryAccess registryAccess = player.registryAccess();

		player.setItemSlot(EquipmentSlot.HEAD, AddonCommand.Equip.makeFullWithEnchantments(AllthemodiumCompat.rl("allthemodium_helmet"), registryAccess));
		player.setItemSlot(EquipmentSlot.CHEST, AddonCommand.Equip.makeFullWithEnchantments(AllthemodiumCompat.rl("allthemodium_chestplate"), registryAccess));
		player.setItemSlot(EquipmentSlot.LEGS, AddonCommand.Equip.makeFullWithEnchantments(AllthemodiumCompat.rl("allthemodium_leggings"), registryAccess));
		player.setItemSlot(EquipmentSlot.FEET, AddonCommand.Equip.makeFullWithEnchantments(AllthemodiumCompat.rl("allthemodium_boots"), registryAccess));

		return AddonCommand.sendEquipedMessage(source);
	}

	private ATMCommand()
	{

	}

}
