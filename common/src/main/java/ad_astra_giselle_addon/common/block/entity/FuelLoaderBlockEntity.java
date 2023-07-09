package ad_astra_giselle_addon.common.block.entity;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.item.ItemStackConsumers;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import ad_astra_giselle_addon.common.item.ItemStackUtils;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class FuelLoaderBlockEntity extends AddonMachineBlockEntity implements BotariumFluidBlock<WrappedBlockFluidContainer>, IRangedWorkingAreaBlockEntity
{
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";

	public static final int SLOTS_FLUID = 2;
	public static final int SLOT_FLUID_SOURCE = 0;
	public static final int SLOT_FLUID_SINK = 1;

	private boolean workingAreaVisible;
	private WrappedBlockFluidContainer fluidTank;

	public FuelLoaderBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.FUEL_LOADER.get(), pos, state);
	}

	@Override
	public void load(CompoundTag nbt)
	{
		super.load(nbt);
		this.workingAreaVisible = nbt.getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
	}

	@Override
	public void saveAdditional(CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		nbt.putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, this.workingAreaVisible);
	}

	@Override
	public int getMaxStackSize()
	{
		return 1;
	}

	@Override
	public WrappedBlockFluidContainer getFluidContainer()
	{
		if (this.fluidTank == null)
		{
			return this.fluidTank = new WrappedBlockFluidContainer(this, new SimpleFluidContainer(tank -> MachinesConfig.FUEL_LOADER_FLUID_CAPACITY, 1, FluidPredicates::isFuel));
		}
		else
		{
			return this.fluidTank;
		}

	}

	@Override
	public int getInventorySize()
	{
		return super.getInventorySize() + this.getSlotsFluid();
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir)
	{
		stack = ItemStackUtils.deriveCount(stack, 1);

		if (slot == this.getSlotFluidSource())
		{
			if (FluidHooks.isFluidContainingItem(stack))
			{
				UniveralFluidHandler itemFluidHandler = UniveralFluidHandler.from(new ItemStackHolder(stack));
				return itemFluidHandler.getFluidTanks().stream().anyMatch(FluidPredicates::isFuel);
			}

			return false;
		}
		else if (slot == this.getSlotFluidSink())
		{
			return dir == null;
		}

		return super.canPlaceItemThroughFace(slot, stack, dir);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir)
	{
		if (slot == this.getSlotFluidSource())
		{
			if (FluidHooks.isFluidContainingItem(stack))
			{
				UniveralFluidHandler itemFluidHandler = UniveralFluidHandler.from(new ItemStackHolder(stack));
				return !itemFluidHandler.getFluidTanks().stream().anyMatch(FluidPredicates::isFuel);
			}

		}
		else if (slot == this.getSlotFluidSink())
		{
			return dir == null;
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
			this.exchangeFuelItemAround();
		}

	}

	public void processTank()
	{
		UniveralFluidHandler tank = UniveralFluidHandler.from(this.getFluidContainer());
		UniveralFluidHandler.fromSafe(this.getItemRef(this.getSlotFluidSource())).ifPresent(source ->
		{
			FluidHooks2.moveFluidAny(source, tank, FluidPredicates::isFuel, false);
		});
		UniveralFluidHandler.fromSafe(this.getItemRef(this.getSlotFluidSink())).ifPresent(sink ->
		{
			FluidHooks2.moveFluidAny(tank, sink, null, false);
		});
	}

	public ItemStackReference getItemRef(int slot)
	{
		return new ItemStackReference(this.getItem(slot), ItemStackConsumers.index(slot, this::setItem));
	}

	public void exchangeFuelItemAround()
	{
		Level level = this.getLevel();
		AABB workingArea = this.getWorkingArea();
		List<Vehicle> vehicles = level.getEntitiesOfClass(Vehicle.class, workingArea);

		for (Vehicle vehicle : vehicles)
		{
			this.giveFuel(vehicle);
		}

	}

	@Override
	public boolean isWorkingAreaVisible()
	{
		return this.workingAreaVisible;
	}

	@Override
	public void setWorkingAreaVisible(boolean visible)
	{
		if (this.isWorkingAreaVisible() != visible)
		{
			this.workingAreaVisible = visible;
			this.setChanged();
		}

	}

	@Override
	public int getWorkingRange()
	{
		return MachinesConfig.FUEL_LOADER_WORKING_RANGE;
	}

	@Override
	public AABB getWorkingArea(double range)
	{
		return this.getWorkingArea(this.getBlockPos(), range);
	}

	public FluidHolder giveFuel(Vehicle vehicle)
	{
		return this.giveFuel(vehicle, MachinesConfig.FUEL_LOADER_FLUID_TRANSFER);
	}

	private FluidHolder giveFuel(Vehicle vehicle, long transfer)
	{
		UniveralFluidHandler from = UniveralFluidHandler.from(this.getFluidContainer());
		UniveralFluidHandler to = UniveralFluidHandler.from(vehicle.getTank());
		return FluidHooks2.moveFluidAny(from, to, FluidPredicates::isFuel, transfer, false);
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
