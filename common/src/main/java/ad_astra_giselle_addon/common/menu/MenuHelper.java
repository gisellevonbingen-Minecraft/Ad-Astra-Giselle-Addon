package ad_astra_giselle_addon.common.menu;

import java.util.function.Consumer;

import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import net.minecraft.world.inventory.Slot;

public class MenuHelper
{
	public static void addConfigSlots(MachineMenu<?> menu, Consumer<SlotConfiguration> addConfigSlot, int index, int[] slots)
	{
		for (int slotNumber : slots)
		{
			Slot slot = menu.getSlot(slotNumber);
			addConfigSlot.accept(new SlotConfiguration(index, slot.x, slot.y));
		}

	}

	private MenuHelper()
	{

	}

}
