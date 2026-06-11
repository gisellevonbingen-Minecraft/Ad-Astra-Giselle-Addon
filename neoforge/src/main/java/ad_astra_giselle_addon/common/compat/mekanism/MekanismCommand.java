package ad_astra_giselle_addon.common.compat.mekanism;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import ad_astra_giselle_addon.common.command.AddonCommand;
import mekanism.api.gear.ModuleData;
import mekanism.common.content.gear.ModuleContainer;
import mekanism.common.content.gear.ModuleHelper;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismItems;
import mekanism.common.util.StorageUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class MekanismCommand
{
	public static int mekasuit(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		CommandSourceStack source = context.getSource();
		ServerPlayer player = source.getPlayerOrException();
		RegistryAccess registryAccess = player.registryAccess();

		player.setItemSlot(EquipmentSlot.HEAD, makeFull(MekanismItems.MEKASUIT_HELMET.get(), registryAccess, AddonMekanismModules.OXYGEN_PROOF_UNIT));
		player.setItemSlot(EquipmentSlot.CHEST, makeFull(MekanismItems.MEKASUIT_BODYARMOR.get(), registryAccess, AddonMekanismModules.HOT_TEMPERATURE_PROOF_UNIT, AddonMekanismModules.ACID_RAIN_PROOF_UNIT));
		player.setItemSlot(EquipmentSlot.LEGS, makeFull(MekanismItems.MEKASUIT_PANTS.get(), registryAccess));
		player.setItemSlot(EquipmentSlot.FEET, makeFull(MekanismItems.MEKASUIT_BOOTS.get(), registryAccess, AddonMekanismModules.GRAVITY_PROOF_UNIT));

		return AddonCommand.sendEquipedMessage(source);
	}

	@SafeVarargs
	private static ItemStack makeFull(ItemMekaSuitArmor item, HolderLookup.Provider provider, Holder<ModuleData<?>>... moduleProviders)
	{
		ItemStack stack = new ItemStack(item);

		for (Holder<ModuleData<?>> moduleProvider : moduleProviders)
		{
			ModuleContainer container = ModuleHelper.get().getModuleContainer(stack);
			container.addModule(provider, stack, moduleProvider, moduleProvider.value().getMaxStackSize());
		}

		return StorageUtils.getFilledEnergyVariant(stack);
	}

	private MekanismCommand()
	{

	}

}
