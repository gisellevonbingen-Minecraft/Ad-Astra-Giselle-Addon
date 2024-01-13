package ad_astra_giselle_addon.common.menu;

import java.util.function.Consumer;

import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import net.minecraft.world.inventory.Slot;

public class MenuHelper
{
	public static void addConfigSlots(MachineMenu<?> menu, Consumer<SlotConfiguration> addConfigSlot, int index, int[] slots)
	{
		for (int i = 0; i < slots.length; i++)
		{
			Slot slot = menu.getSlot(slots[i]);
			addConfigSlot.accept(new SlotConfiguration(index, slot.x, slot.y));
		}

	}

	private MenuHelper()
	{

	}

}
