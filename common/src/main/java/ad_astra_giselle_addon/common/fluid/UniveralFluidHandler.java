package ad_astra_giselle_addon.common.fluid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.botarium.api.fluid.FluidContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import earth.terrarium.botarium.api.fluid.PlatformFluidItemHandler;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class UniveralFluidHandler implements PlatformFluidHandler
{
	private UniveralFluidHandler()
	{

	}

	public abstract long getTankCapacity(int tank);

	@Override
	public boolean supportsInsertion()
	{
		return true;
	}

	@Override
	public boolean supportsExtraction()
	{
		return true;
	}

	@Nullable
	public static Optional<UniveralFluidHandler> fromSafe(ItemStackHolder item)
	{
		if (FluidHooks.isFluidContainingItem(item.getStack()))
		{
			return Optional.of(new UniveralFluidItemHandler(item));
		}
		else
		{
			return Optional.empty();
		}

	}

	public static UniveralFluidHandler from(ItemStackHolder item)
	{
		return new UniveralFluidItemHandler(item);
	}

	@Nullable
	public static Optional<UniveralFluidHandler> fromSafe(BlockEntity blockEntity, @Nullable Direction direction)
	{
		if (FluidHooks.isFluidContainingBlock(blockEntity, direction))
		{
			return Optional.of(new UniveralFluidBlockHandler(blockEntity, direction));
		}
		else
		{
			return Optional.empty();
		}

	}

	public static UniveralFluidHandler from(BlockEntity blockEntity, @Nullable Direction direction)
	{
		return new UniveralFluidBlockHandler(blockEntity, direction);
	}

	public static UniveralFluidHandler from(FluidContainer container)
	{
		return new UniveralFluidContainerHandler(container);
	}

	public static class UniveralFluidItemHandler extends UniveralFluidHandler
	{
		private final ItemStackHolder item;

		public UniveralFluidItemHandler(ItemStackHolder item)
		{
			this.item = item;
		}

		public ItemStackHolder getItem()
		{
			return this.item;
		}

		public PlatformFluidItemHandler getInternalHandler()
		{
			return FluidHooks.getItemFluidManager(this.getItem().getStack());
		}

		@Override
		public long insertFluid(FluidHolder fluid, boolean simulate)
		{
			return this.getInternalHandler().insertFluid(this.getItem(), fluid, simulate);
		}

		@Override
		public FluidHolder extractFluid(FluidHolder fluid, boolean simulate)
		{
			return this.getInternalHandler().extractFluid(this.getItem(), fluid, simulate);
		}

		@Override
		public int getTankAmount()
		{
			return AdAstraGiselleAddon.delegate().getFluidHelper().getTankCount(this.getInternalHandler());
		}

		@Override
		public FluidHolder getFluidInTank(int tank)
		{
			return this.getInternalHandler().getFluidInTank(tank);
		}

		@Override
		public List<FluidHolder> getFluidTanks()
		{
			List<FluidHolder> list = new ArrayList<>();

			for (int i = 0; i < this.getTankAmount(); i++)
			{
				list.add(this.getFluidInTank(i));
			}

			return list;
		}

		@Override
		public long getTankCapacity(int tank)
		{
			return AdAstraGiselleAddon.delegate().getFluidHelper().getCapacity(this.getInternalHandler(), tank);
		}

	}

	public static class UniveralFluidBlockHandler extends UniveralFluidHandler
	{
		private final BlockEntity blockEntity;
		private final Direction direction;

		public UniveralFluidBlockHandler(BlockEntity blockEntity, @Nullable Direction direction)
		{
			this.blockEntity = blockEntity;
			this.direction = direction;
		}

		public BlockEntity getBlockEntity()
		{
			return this.blockEntity;
		}

		public Direction getDirection()
		{
			return this.direction;
		}

		public PlatformFluidHandler getInternalHandler()
		{
			return FluidHooks.getBlockFluidManager(this.getBlockEntity(), this.getDirection());
		}

		@Override
		public long insertFluid(FluidHolder fluid, boolean simulate)
		{
			return this.getInternalHandler().insertFluid(fluid, simulate);
		}

		@Override
		public FluidHolder extractFluid(FluidHolder fluid, boolean simulate)
		{
			return this.getInternalHandler().extractFluid(fluid, simulate);
		}

		@Override
		public int getTankAmount()
		{
			return AdAstraGiselleAddon.delegate().getFluidHelper().getTankCount(this.getInternalHandler());
		}

		@Override
		public FluidHolder getFluidInTank(int tank)
		{
			return this.getInternalHandler().getFluidInTank(tank);
		}

		@Override
		public List<FluidHolder> getFluidTanks()
		{
			List<FluidHolder> list = new ArrayList<>();

			for (int i = 0; i < this.getTankAmount(); i++)
			{
				list.add(this.getFluidInTank(i));
			}

			return list;
		}

		@Override
		public long getTankCapacity(int tank)
		{
			return AdAstraGiselleAddon.delegate().getFluidHelper().getCapacity(this.getInternalHandler(), tank);
		}

	}

	public static class UniveralFluidContainerHandler extends UniveralFluidHandler
	{
		private final FluidContainer container;

		public UniveralFluidContainerHandler(FluidContainer container)
		{
			this.container = container;
		}

		public FluidContainer getContainer()
		{
			return this.container;
		}

		@Override
		public long insertFluid(FluidHolder fluid, boolean simulate)
		{
			return this.getContainer().insertFluid(fluid, simulate);
		}

		@Override
		public FluidHolder extractFluid(FluidHolder fluid, boolean simulate)
		{
			return this.getContainer().extractFluid(fluid, simulate);
		}

		@Override
		public int getTankAmount()
		{
			return this.getContainer().getSize();
		}

		@Override
		public FluidHolder getFluidInTank(int tank)
		{
			return this.getFluidTanks().get(tank);
		}

		@Override
		public List<FluidHolder> getFluidTanks()
		{
			return this.getContainer().getFluids();
		}

		@Override
		public long getTankCapacity(int tank)
		{
			return this.getContainer().getTankCapacity(tank);
		}

	}

}
