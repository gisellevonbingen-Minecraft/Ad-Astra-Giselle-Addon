package ad_astra_giselle_addon.common.menu;

import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import ad_astra_giselle_addon.common.registries.AddonMenuTypes;
import earth.terrarium.ad_astra.networking.NetworkHandling;
import earth.terrarium.ad_astra.networking.packets.server.MachineInfoPacket;
import earth.terrarium.ad_astra.screen.menu.AbstractMachineMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class FuelLoaderContainerMenu extends AbstractMachineMenu<FuelLoaderBlockEntity>
{
	public FuelLoaderContainerMenu(int windowId, Inventory inv, FuelLoaderBlockEntity blockEntity)
	{
		super(AddonMenuTypes.FUEL_LOADER.get(), windowId, inv, blockEntity, getSlots(blockEntity));
	}

	public static Slot[] getSlots(FuelLoaderBlockEntity blockEntity)
	{
		List<Slot> list = new ArrayList<>();

		for (int i = blockEntity.getSlotFluidStart(); i < blockEntity.getSlotFluidEnd(); i++)
		{
			list.add(new Slot(blockEntity, i, 95, 28 + 30 * i));
		}

		return list.toArray(new Slot[0]);
	}

	@Override
	public int getPlayerInventoryOffset()
	{
		return 16;
	}

	@Override
	public void syncClientScreen()
	{
		FuelLoaderBlockEntity machine = this.getMachine();
		Player player = this.player;
		NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(0L, machine.getFluidContainer().getFluids()), player);
	}

}
