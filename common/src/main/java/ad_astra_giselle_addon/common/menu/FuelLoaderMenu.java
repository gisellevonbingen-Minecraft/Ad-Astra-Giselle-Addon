package ad_astra_giselle_addon.common.menu;

import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import earth.terrarium.adastra.common.menus.configuration.MenuConfiguration;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FuelLoaderMenu extends AddonMachineMenu<FuelLoaderBlockEntity>
{
	public static final int FLUID_BAR_LEFT = 68;
	public static final int FLUID_BAR_TOP = 28;

	public FuelLoaderMenu(int windowId, Inventory inv, FuelLoaderBlockEntity blockEntity)
	{
		super(AddonMenuTypes.FUEL_LOADER.get(), windowId, inv, blockEntity);
	}

	@Override
	protected void addMenuSlots()
	{
		FuelLoaderBlockEntity blockEntity = this.getEntity();
		int[] slots = FuelLoaderBlockEntity.FLUID_SLOTS;

		for (int i = 0; i < slots.length; i++)
		{
			this.addSlot(new Slot(blockEntity, slots[i], 95, 28 + 30 * i)
			{
				@Override
				public boolean mayPlace(ItemStack pStack)
				{
					return blockEntity.canPlaceItemThroughFace(this.index, pStack, null);
				}

			});

		}

	}

	@Override
	protected void addConfigSlots()
	{
		FuelLoaderBlockEntity blockEntity = this.getEntity();
		MenuHelper.addConfigSlots(this, this::addConfigSlot, 0, FuelLoaderBlockEntity.FLUID_SOURCE_SLOTS);
		MenuHelper.addConfigSlots(this, this::addConfigSlot, 1, FuelLoaderBlockEntity.FLUID_SINK_SLOTS);
		this.addConfigSlot(new FluidConfiguration(2, FLUID_BAR_LEFT, FLUID_BAR_TOP, blockEntity.getFluidContainer(), 0));
	}

	@Override
	protected void addConfigSlot(MenuConfiguration configuration)
	{
		super.addConfigSlot(configuration);
	}

	@Override
	public int startIndex()
	{
		return FuelLoaderBlockEntity.FLUID_SLOTS[0];
	}

	@Override
	public int getContainerInputEnd()
	{
		int[] slots = FuelLoaderBlockEntity.FLUID_SLOTS;
		return slots[slots.length - 1];
	}

	@Override
	public int getInventoryStart()
	{
		return FuelLoaderBlockEntity.CONTAINER_SIZE;
	}

	@Override
	public int getPlayerInvXOffset()
	{
		return super.getPlayerInvXOffset();
	}

	@Override
	public int getPlayerInvYOffset()
	{
		return 100;
	}

}
