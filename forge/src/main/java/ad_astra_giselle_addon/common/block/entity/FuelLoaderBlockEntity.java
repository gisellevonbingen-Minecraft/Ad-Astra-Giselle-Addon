package ad_astra_giselle_addon.common.block.entity;

import java.util.List;

import javax.annotation.Nullable;

import ad_astra_giselle_addon.common.config.AddonConfigs;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.item.ItemStackConsumers;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;
import ad_astra_giselle_addon.common.registries.AddonBlockEntityTypes;
import earth.terrarium.ad_astra.blocks.machines.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.entities.vehicles.Vehicle;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.SimpleUpdatingFluidContainer;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class FuelLoaderBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock
{
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";
	public static final String DATA_WORKED_KEY = "worked";

	public static final int SLOTS_FLUID = 2;
	public static final int SLOT_FLUID_SOURCE = 0;
	public static final int SLOT_FLUID_SINK = 1;

	private SimpleUpdatingFluidContainer fluidTank;

	public FuelLoaderBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.FUEL_LOADER.get(), pos, state);
	}

	@Override
	public int getMaxStackSize()
	{
		return 1;
	}

	@Override
	public SimpleUpdatingFluidContainer getFluidContainer()
	{
		if (this.fluidTank == null)
		{
			return this.fluidTank = new SimpleUpdatingFluidContainer(this, tank -> (long) AddonConfigs.Common.machines.fuelLoader_capacity.get(), 1, FluidPredicates::isFuel);
		}
		else
		{
			return this.fluidTank;
		}

	}

	@Override
	public void update()
	{
		this.setChanged();
		this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
	}

	@Override
	public int getInventorySize()
	{
		return super.getInventorySize() + this.getSlotsFluid();
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack)
	{
		return this.canPlaceItemThroughFace(slot, stack, null);
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir)
	{
		if (FluidHooks.isFluidContainingItem(stack))
		{
			UniveralFluidHandler itemFluidHandler = UniveralFluidHandler.from(new ItemStackHolder(stack));

			if (slot == this.getSlotFluidSource())
			{
				return !FluidHooks2.extractFluid(itemFluidHandler, FluidPredicates::isFuel, Integer.MAX_VALUE, true).isEmpty();
			}
			else if (slot == this.getSlotFluidSink())
			{
				return !FluidHooks2.insertFluidAny(itemFluidHandler, this.getFluidContainer().getFluids(), true).isEmpty();
			}

		}

		return super.canPlaceItemThroughFace(slot, stack, dir);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir)
	{
		if (FluidHooks.isFluidContainingItem(stack))
		{
			UniveralFluidHandler itemFluidHandler = UniveralFluidHandler.from(new ItemStackHolder(stack));

			if (slot == this.getSlotFluidSource())
			{
				return FluidHooks2.extractFluid(itemFluidHandler, FluidPredicates::isFuel, Integer.MAX_VALUE, true).isEmpty();
			}
			else if (slot == this.getSlotFluidSink())
			{
				return FluidHooks2.insertFluidAny(itemFluidHandler, this.getFluidContainer().getFluids(), true).isEmpty();
			}

		}

		return super.canTakeItemThroughFace(slot, stack, dir);
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player)
	{
		return new FuelLoaderMenu(windowId, inv, this);
	}

	@Override
	public void tick()
	{
		Level level = this.getLevel();

		if (!level.isClientSide())
		{
			this.processTank();
			boolean worked = this.exchangeFuelItemAround();
			this.setWorked(worked);
		}

	}

	public void processTank()
	{
		UniveralFluidHandler tank = UniveralFluidHandler.from(this.getFluidContainer());
		UniveralFluidHandler.fromSafe(this.getItemRef(this.getSlotFluidSource())).ifPresent(source ->
		{
			FluidHooks2.moveFluidAny(source, tank, FluidPredicates::isFuel, Integer.MAX_VALUE, false);
		});
		UniveralFluidHandler.fromSafe(this.getItemRef(this.getSlotFluidSink())).ifPresent(sink ->
		{
			FluidHooks2.moveFluidAny(tank, sink, null, Integer.MAX_VALUE, false);
		});
	}

	public ItemStackReference getItemRef(int slot)
	{
		return new ItemStackReference(this.getItem(slot), ItemStackConsumers.index(slot, this::setItem));
	}

	public boolean exchangeFuelItemAround()
	{
		Level level = this.getLevel();
		AABB workingArea = this.getWorkingArea();
		List<Vehicle> vehicles = level.getEntitiesOfClass(Vehicle.class, workingArea);
		boolean worked = false;

		for (Vehicle vehicle : vehicles)
		{
			if (this.giveFuel(vehicle))
			{
				worked = true;
			}

		}

		return worked;
	}

	@Override
	public AABB getRenderBoundingBox()
	{
		return this.getWorkingArea();
	}

	public boolean isWorkingAreaVisible()
	{
		return this.getPersistentData().getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
	}

	public void setWorkingAreaVisible(boolean visible)
	{
		if (this.isWorkingAreaVisible() != visible)
		{
			this.getPersistentData().putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, visible);
			this.setChanged();
		}

	}

	public int getWorkingRange()
	{
		return AddonConfigs.Common.machines.fuelLoader_range.get();
	}

	public AABB getWorkingArea()
	{
		return this.getWorkingArea(this.getWorkingRange());
	}

	public AABB getWorkingArea(double range)
	{
		return this.getWorkingArea(this.getBlockPos(), range);
	}

	public AABB getWorkingArea(BlockPos pos, double range)
	{
		double half = range / 2.0D;
		return new AABB(pos).inflate(range, half, range).move(0.0D, half, 0.0D);
	}

	public boolean isWorked()
	{
		return this.getPersistentData().getBoolean(DATA_WORKED_KEY);
	}

	protected void setWorked(boolean newWorked)
	{
		if (this.isWorked() != newWorked)
		{
			this.getPersistentData().putBoolean(DATA_WORKED_KEY, newWorked);
			this.setChanged();
		}

	}

	public boolean giveFuel(Vehicle vehicle)
	{
		long transfer = AddonConfigs.Common.machines.fuelLoader_transfer.get();
		UniveralFluidHandler from = UniveralFluidHandler.from(this.getFluidContainer());
		UniveralFluidHandler to = UniveralFluidHandler.from(vehicle.getTank());
		return FluidHooks2.moveFluidAny(from, to, FluidPredicates::isFuel, transfer, false).isEmpty();
	}

	public int getSlotFluidStart()
	{
		return 0;
	}

	public int getSlotFluidEnd()
	{
		return this.getSlotFluidStart() + this.getSlotsFluid();
	}

	public int getSlotsFluid()
	{
		return SLOTS_FLUID;
	}

	public int getSlotFluidSource()
	{
		return this.getSlotFluidStart() + SLOT_FLUID_SOURCE;
	}

	public int getSlotFluidSink()
	{
		return this.getSlotFluidStart() + SLOT_FLUID_SINK;
	}

}
