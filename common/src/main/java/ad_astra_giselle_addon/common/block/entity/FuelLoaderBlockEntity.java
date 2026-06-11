package ad_astra_giselle_addon.common.block.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.config.MachinesConfig;
import ad_astra_giselle_addon.common.entity.VehicleFuelInformation;
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
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import earth.terrarium.adastra.common.utils.TransferUtils;
import earth.terrarium.common_storage_lib.context.impl.IsolatedSlotContext;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage;
import earth.terrarium.common_storage_lib.fluid.util.FluidProvider;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;

public class FuelLoaderBlockEntity extends ContainerMachineBlockEntity implements FluidProvider.BlockEntity, IRangedWorkingAreaBlockEntity
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

	public static List<TagKey<Fluid>> getFluidTags()
	{
		List<TagKey<Fluid>> list = new ArrayList<>();
		list.add(ModFluidTags.TIER_1_ROVER_FUEL);
		Rocket.ROCKET_TO_PROPERTIES.values().stream().map(i -> i.fuel()).forEach(list::add);

		return list;
	}

	public static boolean isFuel(FluidResource fluidHolder)
	{
		return FuelLoaderBlockEntity.isFuel(fluidHolder.getType());
	}

	public static boolean isFuel(Fluid fluid)
	{
		return getFluidTags().stream().anyMatch(tag -> FluidPredicates.hasTag(fluid, tag));
	}

	private boolean workingAreaVisible;
	private SimpleFluidStorage fluidTank;

	public FuelLoaderBlockEntity(BlockPos pos, BlockState state)
	{
		super(pos, state, CONTAINER_SIZE);
		this.fluidTank = new SimpleFluidStorage(this, ModDataManagers.FLUID_CONTENTS, 1, FluidAmounts.toPlatformAmount(MachinesConfig.FUEL_LOADER.fluidCapacity)).filter(0, FuelLoaderBlockEntity::isFuel);
	}

	@Override
	protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider)
	{
		super.loadAdditional(tag, provider);
		this.workingAreaVisible = tag.getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider)
	{
		super.saveAdditional(tag, provider);
		tag.putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, this.workingAreaVisible);
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv, Player player)
	{
		return new FuelLoaderMenu(windowId, inv, this);
	}

	@Override
	public CommonStorage<FluidResource> getFluids(@Nullable Direction direction)
	{
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

		CommonStorage<FluidResource> fluidContainer = this.getFluids(null);
		long fluidCapacity = FluidAmounts.toPlatformAmount(MachinesConfig.FUEL_LOADER.fluidCapacity);
		TransferUtils.pushFluidNearby(this, pos, fluidContainer, fluidCapacity, 0, sideConfig.get(2), filter);
		TransferUtils.pullFluidNearby(this, pos, fluidContainer, fluidCapacity, 0, sideConfig.get(2), filter);
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
			CommonStorage<FluidResource> fluidContainer = new IsolatedSlotContext(stack).find(FluidApi.ITEM);

			if (fluidContainer != null)
			{
				for (int i = 0; i < fluidContainer.size(); i++)
				{
					if (isFuel(fluidContainer.get(i).getResource()))
					{
						return true;
					}

				}

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
			CommonStorage<FluidResource> fluidContainer = new IsolatedSlotContext(stack).find(FluidApi.ITEM);

			if (fluidContainer != null)
			{
				for (int i = 0; i < fluidContainer.size(); i++)
				{
					if (isFuel(fluidContainer.get(i).getResource()))
					{
						return false;
					}

				}

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

		if (this.canFunction())
		{
			this.processTank();
			this.exchangeFuelItemAround();
		}

	}

	public void processTank()
	{
		CommonStorage<FluidResource> tank = this.getFluids(null);

		for (int slot : FLUID_SOURCE_SLOTS)
		{
			CommonStorage<FluidResource> source = ContainerHelper.getItem(this, slot).find(FluidApi.ITEM);

			if (source != null)
			{
				FluidUtils2.moveFluidAny(source, tank, FuelLoaderBlockEntity::isFuel, false);
			}

		}

		for (int slot : FLUID_SINK_SLOTS)
		{
			CommonStorage<FluidResource> sink = ContainerHelper.getItem(this, slot).find(FluidApi.ITEM);

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
			this.setChanged();
		}

	}

	@Override
	public int getWorkingRange()
	{
		return MachinesConfig.FUEL_LOADER.workingRange;
	}

	@Override
	public AABB getWorkingArea(double range)
	{
		return this.getWorkingArea(this.getBlockPos(), range);
	}

	public ResourceStack<FluidResource> giveFuel(Vehicle vehicle)
	{
		return this.giveFuel(vehicle, FluidAmounts.toPlatformAmount(MachinesConfig.FUEL_LOADER.fluidTransfer));
	}

	private ResourceStack<FluidResource> giveFuel(Vehicle vehicle, long transfer)
	{
		VehicleFuelInformation fuelInfo = VehicleHelper.getFuelInformation(vehicle);

		if (fuelInfo != null)
		{
			CommonStorage<FluidResource> from = this.getFluids(null);
			return FluidUtils2.moveFluidAny(from, fuelInfo.fuelTank(), fluid -> fluid.is(fuelInfo.fuelTag()), transfer, false);
		}
		else
		{
			return ResourceStack.EMPTY_FLUID;
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
