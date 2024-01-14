package ad_astra_giselle_addon.common.block.entity;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.entity.VehicleHelper;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import ad_astra_giselle_addon.common.item.ItemStackUtils;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;
import ad_astra_giselle_addon.common.world.ContainerHelper;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.Configuration;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationEntry;
import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.ItemFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class FuelLoaderBlockEntity extends ContainerMachineBlockEntity implements BotariumFluidBlock<WrappedBlockFluidContainer>, IRangedWorkingAreaBlockEntity
{
	public static final List<ConfigurationEntry> SIDE_CONFIG = List.of(//
			new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_INPUT_SLOTS), //
			new ConfigurationEntry(ConfigurationType.SLOT, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS), //

			new ConfigurationEntry(ConfigurationType.FLUID, Configuration.NONE, ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID));

	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";

	public static final int[] FLUID_SOURCE_SLOTS = {0};
	public static final int[] FLUID_SINK_SLOTS = {1};
	public static final int[] FLUID_SLOTS = ArrayUtils.addAll(FLUID_SOURCE_SLOTS, FLUID_SINK_SLOTS);
	public static final int CONTAINER_SIZE = FLUID_SLOTS.length;

	private boolean workingAreaVisible;
	private WrappedBlockFluidContainer fluidTank;

	public FuelLoaderBlockEntity(BlockPos pos, BlockState state)
	{
		super(pos, state, CONTAINER_SIZE);
	}

	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		this.workingAreaVisible = tag.getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
	}

	@Override
	public void saveAdditional(CompoundTag tag)
	{
		super.saveAdditional(tag);
		tag.putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, this.workingAreaVisible);
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player)
	{
		return new FuelLoaderMenu(windowId, inv, this);
	}

	@Override
	public WrappedBlockFluidContainer getFluidContainer()
	{
		if (this.fluidTank == null)
		{
			this.fluidTank = new WrappedBlockFluidContainer(this, new SimpleFluidContainer(tank -> MachinesConfig.FUEL_LOADER_FLUID_CAPACITY, 1, FluidPredicates::isFuel));
		}

		return this.fluidTank;
	}

	@Override
	public void tickSideInteractions(BlockPos pos, Predicate<Direction> filter, List<ConfigurationEntry> sideConfig)
	{
		super.tickSideInteractions(pos, filter, sideConfig);

		TransferUtils.pushItemsNearby(this, pos, FLUID_SOURCE_SLOTS, sideConfig.get(0), filter);
		TransferUtils.pullItemsNearby(this, pos, FLUID_SOURCE_SLOTS, sideConfig.get(0), filter);
		TransferUtils.pushItemsNearby(this, pos, FLUID_SINK_SLOTS, sideConfig.get(1), filter);
		TransferUtils.pullItemsNearby(this, pos, FLUID_SINK_SLOTS, sideConfig.get(1), filter);

		TransferUtils.pushFluidNearby(this, pos, this.getFluidContainer(), MachinesConfig.FUEL_LOADER_FLUID_CAPACITY, 0, sideConfig.get(2), filter);
		TransferUtils.pullFluidNearby(this, pos, this.getFluidContainer(), MachinesConfig.FUEL_LOADER_FLUID_CAPACITY, 0, sideConfig.get(2), filter);
	}

	@Override
	public int getMaxStackSize()
	{
		return 1;
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir)
	{
		stack = ItemStackUtils.deriveCount(stack, 1);

		if (ArrayUtils.contains(FLUID_SOURCE_SLOTS, slot))
		{
			ItemFluidContainer fluidContainer = FluidContainer.of(new ItemStackHolder(stack));

			if (fluidContainer != null)
			{
				return fluidContainer.getFluids().stream().anyMatch(FluidPredicates::isFuel);
			}

			return false;
		}
		else if (ArrayUtils.contains(FLUID_SINK_SLOTS, slot))
		{
			return dir == null;
		}

		return super.canPlaceItemThroughFace(slot, stack, dir);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir)
	{
		if (ArrayUtils.contains(FLUID_SOURCE_SLOTS, slot))
		{
			ItemFluidContainer fluidContainer = FluidContainer.of(new ItemStackHolder(stack));

			if (fluidContainer != null)
			{
				return !fluidContainer.getFluids().stream().anyMatch(FluidPredicates::isFuel);
			}

		}
		else if (ArrayUtils.contains(FLUID_SINK_SLOTS, slot))
		{
			return dir == null;
		}

		return super.canTakeItemThroughFace(slot, stack, dir);
	}

	@Override
	public void serverTick(ServerLevel level, long time, BlockState state, BlockPos pos)
	{
		super.serverTick(level, time, state, pos);

		this.processTank();
		this.exchangeFuelItemAround();
	}

	public void processTank()
	{
		FluidContainer tank = FluidContainer.of(this, null);

		for (int slot : FLUID_SOURCE_SLOTS)
		{
			ItemFluidContainer source = FluidContainer.of(ContainerHelper.getItem(this, slot));

			if (source != null)
			{
				FluidUtils2.moveFluidAny(source, tank, FluidPredicates::isFuel, false);
			}

		}

		for (int slot : FLUID_SINK_SLOTS)
		{
			ItemFluidContainer sink = FluidContainer.of(ContainerHelper.getItem(this, slot));

			if (sink != null)
			{
				FluidUtils2.moveFluidAny(tank, sink, null, false);
			}

		}

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
			this.sync();
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
		FluidContainer to = VehicleHelper.getFluidContainer(vehicle);

		if (to != null)
		{
			FluidContainer from = FluidContainer.of(this, null);
			FluidHolder moved = FluidUtils2.moveFluidAny(from, to, FluidPredicates::isFuel, transfer, false);
			return moved;
		}
		else
		{
			return FluidHolder.empty();
		}

	}

	@Override
	public List<ConfigurationEntry> getDefaultConfig()
	{
		return SIDE_CONFIG;
	}

	@Override
	public int[] getSlotsForFace(Direction pSide)
	{
		return FLUID_SLOTS;
	}

}
