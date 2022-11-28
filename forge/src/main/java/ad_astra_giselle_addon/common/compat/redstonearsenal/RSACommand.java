package ad_astra_giselle_addon.common.compat.redstonearsenal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.command.AddonCommand;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;

public class RSACommand
{
	public static int flux_armor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		CommandSourceStack source = context.getSource();
		ServerPlayer player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlot.HEAD, AddonCommand.Equip.makeFull(RedstoneArsenalCompat.rl("flux_helmet"), AddonEnchantments.SPACE_BREATHING.get()));
		player.setItemSlot(EquipmentSlot.CHEST, AddonCommand.Equip.makeFull(RedstoneArsenalCompat.rl("flux_chestplate"), AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
		player.setItemSlot(EquipmentSlot.LEGS, AddonCommand.Equip.makeFull(RedstoneArsenalCompat.rl("flux_leggings")));
		player.setItemSlot(EquipmentSlot.FEET, AddonCommand.Equip.makeFull(RedstoneArsenalCompat.rl("flux_boots")));

		return AddonCommand.sendEquipedMessage(source);
	}

	private RSACommand()
	{

	}

}
