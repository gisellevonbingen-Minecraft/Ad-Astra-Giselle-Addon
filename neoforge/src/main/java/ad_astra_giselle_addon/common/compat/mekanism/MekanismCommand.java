package ad_astra_giselle_addon.common.compat.mekanism;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.command.AddonCommand;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.common.content.gear.ModuleContainer;
import mekanism.common.content.gear.ModuleHelper;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismItems;
import mekanism.common.util.StorageUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class MekanismCommand
{
	public static int mekasuit(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		CommandSourceStack source = context.getSource();
		ServerPlayer player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlot.HEAD, makeFull(MekanismItems.MEKASUIT_HELMET.get(), AddonMekanismModules.OXYGEN_PROOF_UNIT));
		player.setItemSlot(EquipmentSlot.CHEST, makeFull(MekanismItems.MEKASUIT_BODYARMOR.get(), AddonMekanismModules.HOT_TEMPERATURE_PROOF_UNIT, AddonMekanismModules.ACID_RAIN_PROOF_UNIT));
		player.setItemSlot(EquipmentSlot.LEGS, makeFull(MekanismItems.MEKASUIT_PANTS.get()));
		player.setItemSlot(EquipmentSlot.FEET, makeFull(MekanismItems.MEKASUIT_BOOTS.get(), AddonMekanismModules.GRAVITY_PROOF_UNIT));

		return AddonCommand.sendEquipedMessage(source);
	}

	private static ItemStack makeFull(ItemMekaSuitArmor item, IModuleDataProvider<?>... moduleProviders)
	{
		ItemStack stack = new ItemStack(item);
		ModuleContainer moduleContainer = ModuleHelper.get().getModuleContainer(stack).get();

		for (IModuleDataProvider<?> moduleProvider : moduleProviders)
		{
			moduleContainer.addModule(moduleProvider, moduleProvider.getModuleData().getMaxStackSize());
		}

		return StorageUtils.getFilledEnergyVariant(stack);
	}

	private MekanismCommand()
	{

	}

}
