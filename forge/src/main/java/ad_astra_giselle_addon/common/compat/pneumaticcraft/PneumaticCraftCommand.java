package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.command.AddonCommand;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerItem;
import me.desht.pneumaticcraft.common.core.ModItems;
import me.desht.pneumaticcraft.common.item.PneumaticArmorItem;
import me.desht.pneumaticcraft.common.util.UpgradableItemUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class PneumaticCraftCommand
{
	public static int pneumatic_armor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		CommandSourceStack source = context.getSource();
		ServerPlayer player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlot.HEAD, makeFull(ModItems.PNEUMATIC_HELMET.get(), AddonPNCUpgrades.SPACE_BREATHING.get()));
		player.setItemSlot(EquipmentSlot.CHEST, makeFull(ModItems.PNEUMATIC_CHESTPLATE.get(), AddonPNCUpgrades.SPACE_FIRE_PROOF.get(), AddonPNCUpgrades.ACID_RAIN_PROOF.get()));
		player.setItemSlot(EquipmentSlot.LEGS, makeFull(ModItems.PNEUMATIC_LEGGINGS.get()));
		player.setItemSlot(EquipmentSlot.FEET, makeFull(ModItems.PNEUMATIC_BOOTS.get(), AddonPNCUpgrades.GRAVITY_NORMALIZING.get()));

		return AddonCommand.sendEquipedMessage(source);
	}

	private static ItemStack makeFull(PneumaticArmorItem item, PNCUpgrade... upgrades)
	{
		ItemStack stack = new ItemStack(item);
		ItemStackHandler upgradeInventory = new ItemStackHandler(UpgradableItemUtils.UPGRADE_INV_SIZE);

		for (int i = 0; i < upgrades.length; i++)
		{
			PNCUpgrade upgrade = upgrades[i];
			upgradeInventory.setStackInSlot(i, new ItemStack(upgrade.getItem(upgrade.getMaxTier())));
		}

		UpgradableItemUtils.setUpgrades(stack, upgradeInventory);

		IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);
		airHandler.addAir((int) (airHandler.getBaseVolume() * airHandler.maxPressure()));

		return stack;
	}

	private PneumaticCraftCommand()
	{

	}

}
