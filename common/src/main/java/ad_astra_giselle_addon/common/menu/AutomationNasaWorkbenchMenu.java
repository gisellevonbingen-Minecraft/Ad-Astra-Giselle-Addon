package ad_astra_giselle_addon.common.menu;

import ad_astra_giselle_addon.common.block.entity.AutomationNasaWorkbenchBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.slots.CustomSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class AutomationNasaWorkbenchMenu extends AddonMachineMenu<AutomationNasaWorkbenchBlockEntity>
{
	public AutomationNasaWorkbenchMenu(int windowId, Inventory inv, AutomationNasaWorkbenchBlockEntity blockEntity)
	{
		super(AddonMenuTypes.AUTOMATION_NASA_WORKBENCH.get(), windowId, inv, blockEntity);
	}

	@Override
	protected void addMenuSlots()
	{
		super.addMenuSlots();

		AutomationNasaWorkbenchBlockEntity blockEntity = this.getEntity();
		int[] inputSlots = AutomationNasaWorkbenchBlockEntity.INPUT_SLOTS;
		int[] outputSlots = AutomationNasaWorkbenchBlockEntity.OUTPUT_SLOTS;

		// Nose
		this.addSlot(new Slot(blockEntity, inputSlots[0], 36, 20));

		// Body
		this.addSlot(new Slot(blockEntity, inputSlots[1], 27, 38));
		this.addSlot(new Slot(blockEntity, inputSlots[2], 45, 38));
		this.addSlot(new Slot(blockEntity, inputSlots[3], 27, 56));
		this.addSlot(new Slot(blockEntity, inputSlots[4], 45, 56));
		this.addSlot(new Slot(blockEntity, inputSlots[5], 27, 74));
		this.addSlot(new Slot(blockEntity, inputSlots[6], 45, 74));

		// Left fin
		this.addSlot(new Slot(blockEntity, inputSlots[7], 9, 92));

		// Tank
		this.addSlot(new Slot(blockEntity, inputSlots[8], 27, 92));
		this.addSlot(new Slot(blockEntity, inputSlots[9], 45, 92));

		// Right fin
		this.addSlot(new Slot(blockEntity, inputSlots[10], 63, 92));

		// Left fin
		this.addSlot(new Slot(blockEntity, inputSlots[11], 9, 110));

		// Engine
		this.addSlot(new Slot(blockEntity, inputSlots[12], 36, 110));

		// Right fin
		this.addSlot(new Slot(blockEntity, inputSlots[13], 63, 110));

		// Output
		this.addSlot(CustomSlot.noPlace(blockEntity, outputSlots[0], 109, 56));
	}

	@Override
	protected void addConfigSlots()
	{
		AutomationNasaWorkbenchBlockEntity blockEntity = this.getEntity();
		MenuHelper.addConfigSlots(this, this::addConfigSlot, 0, AutomationNasaWorkbenchBlockEntity.INPUT_SLOTS);
		MenuHelper.addConfigSlots(this, this::addConfigSlot, 1, AutomationNasaWorkbenchBlockEntity.OUTPUT_SLOTS);
		this.addConfigSlot(new EnergyConfiguration(2, 147, 50, blockEntity.getEnergyStorage()));
	}

	@Override
	public int startIndex()
	{
		return AutomationNasaWorkbenchBlockEntity.INPUT_SLOTS[0];
	}

	@Override
	public int getContainerInputEnd()
	{
		int[] slots = AutomationNasaWorkbenchBlockEntity.INPUT_SLOTS;
		return slots[slots.length - 1];
	}

	@Override
	public int getInventoryStart()
	{
		return AutomationNasaWorkbenchBlockEntity.CONTAINER_SIZE;
	}

	@Override
	public int getPlayerInvXOffset()
	{
		return super.getPlayerInvXOffset();
	}

	@Override
	public int getPlayerInvYOffset()
	{
		return 142;
	}

}
