package ad_astra_giselle_addon.common.compat.mekanism;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.command.AddonCommand;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.gear.shared.ModuleEnergyUnit;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismItems;
import mekanism.common.registries.MekanismModules;
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

		player.setItemSlot(EquipmentSlot.HEAD, makeFull(MekanismItems.MEKASUIT_HELMET.get(), AddonMekanismModules.SPACE_BREATHING_UNIT));
		player.setItemSlot(EquipmentSlot.CHEST, makeFull(MekanismItems.MEKASUIT_BODYARMOR.get(), AddonMekanismModules.SPACE_FIRE_PROOF_UNIT, AddonMekanismModules.ACID_RAIN_PROOF_UNIT));
		player.setItemSlot(EquipmentSlot.LEGS, makeFull(MekanismItems.MEKASUIT_PANTS.get()));
		player.setItemSlot(EquipmentSlot.FEET, makeFull(MekanismItems.MEKASUIT_BOOTS.get(), AddonMekanismModules.GRAVITY_NORMALIZING_UNIT));

		return AddonCommand.sendEquipedMessage(source);
	}

	private static ItemStack makeFull(ItemMekaSuitArmor item, IModuleDataProvider<?>... moduleProviders)
	{
		ItemStack stack = new ItemStack(item);

		for (IModuleDataProvider<?> moduleProvider : moduleProviders)
		{
			item.addModule(stack, moduleProvider.getModuleData());
		}

		IModule<ModuleEnergyUnit> energyUnit = item.getModule(stack, MekanismModules.ENERGY_UNIT);
		FloatingLong maxEnergy = energyUnit == null ? MekanismConfig.gear.mekaSuitBaseEnergyCapacity.get() : energyUnit.getCustomInstance().getEnergyCapacity(energyUnit);

		return StorageUtils.getFilledEnergyVariant(stack, maxEnergy);
	}

	private MekanismCommand()
	{

	}

}
