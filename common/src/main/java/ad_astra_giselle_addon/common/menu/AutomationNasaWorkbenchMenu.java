package ad_astra_giselle_addon.common.menu;

import java.util.Collections;

import ad_astra_giselle_addon.common.block.entity.AutomationNasaWorkbenchBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.networking.packet.server.MachineInfoPacket;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AutomationNasaWorkbenchMenu extends AbstractMachineMenu<AutomationNasaWorkbenchBlockEntity>
{
	private DataSlot cookTime;
	private DataSlot cookTimeTotal;

	public AutomationNasaWorkbenchMenu(int windowId, Inventory inv, AutomationNasaWorkbenchBlockEntity blockEntity)
	{
		super(AddonMenuTypes.AUTOMATION_NASA_WORKBENCH.get(), windowId, inv, blockEntity, getSlots(blockEntity));
		this.cookTime = this.addDataSlot(DataSlot.standalone());
		this.cookTimeTotal = this.addDataSlot(DataSlot.standalone());
	}

	public static Slot[] getSlots(AutomationNasaWorkbenchBlockEntity blockEntity)
	{
		return new Slot[]{
				// Nose
				new Slot(blockEntity, 0, 36, 18 * 1 + 2),

				// Body
				new Slot(blockEntity, 1, 27, 18 * 2 + 2), //
				new Slot(blockEntity, 2, 45, 18 * 2 + 2), //
				new Slot(blockEntity, 3, 27, 18 * 3 + 2), //
				new Slot(blockEntity, 4, 45, 18 * 3 + 2), //
				new Slot(blockEntity, 5, 27, 18 * 4 + 2), //
				new Slot(blockEntity, 6, 45, 18 * 4 + 2), //

				// Left fin
				new Slot(blockEntity, 7, 9, 18 * 5 + 2),

				// Tank
				new Slot(blockEntity, 8, 27, 18 * 5 + 2), //
				new Slot(blockEntity, 9, 45, 18 * 5 + 2), //

				// Right fin
				new Slot(blockEntity, 10, 63, 18 * 5 + 2),

				// Left fin
				new Slot(blockEntity, 11, 9, 18 * 6 + 2),

				// Engine
				new Slot(blockEntity, 12, 36, 18 * 6 + 2),

				// Right fin
				new Slot(blockEntity, 13, 63, 18 * 6 + 2),

				// Output
				new Slot(blockEntity, 14, 109, 56)
				{
					@Override
					public boolean mayPlace(ItemStack stack)
					{
						return false;
					}
				}};
	}

	@Override
	public int getPlayerInventoryOffset()
	{
		return 58;
	}

	@Override
	public void syncClientScreen()
	{
		AutomationNasaWorkbenchBlockEntity machine = this.getMachine();
		Player player = this.player;
		NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage(null).getStoredEnergy(), Collections.emptyList()), player);

		this.cookTime.set(machine.getCookTime());
		this.cookTimeTotal.set(machine.getCookTimeTotal());
	}

	public DataSlot getCookTime()
	{
		return this.cookTime;
	}

	public DataSlot getCookTimeTotal()
	{
		return this.cookTimeTotal;
	}

}
