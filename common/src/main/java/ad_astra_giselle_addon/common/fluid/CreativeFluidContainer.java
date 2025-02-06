package ad_astra_giselle_addon.common.fluid;

import java.util.List;
import java.util.function.BiPredicate;

import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.FluidSnapshot;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class CreativeFluidContainer implements FluidContainer
{
	public static final Long CAPACITY = (long) Integer.MAX_VALUE;

	private final BiPredicate<Integer, FluidHolder> fluidFilter;
	private FluidHolder fluid;

	public CreativeFluidContainer(BiPredicate<Integer, FluidHolder> fluidFilter)
	{
		this.fluidFilter = fluidFilter;
	}

	@Override
	public long insertFluid(FluidHolder fluid, boolean simulate)
	{
		if (this.isEmpty() && this.fluidFilter.test(0, fluid))
		{
			return fluid.getFluidAmount();
		}
		else
		{
			return 0;
		}

	}

	@Override
	public FluidHolder extractFluid(FluidHolder fluid, boolean simulate)
	{
		if (this.isEmpty() || !this.fluidFilter.test(0, fluid) || !this.fluid.matches(fluid))
		{
			return FluidHolder.empty();
		}
		else
		{
			return this.fluid.copyWithAmount(fluid.getFluidAmount());
		}

	}

	@Override
	public void setFluid(int slot, FluidHolder fluid)
	{
		if (slot == 0)
		{
			this.fluid = fluid.copyHolder();
		}

	}

	@Override
	public List<FluidHolder> getFluids()
	{
		if (this.isEmpty())
		{
			return List.of(FluidHolder.empty());
		}
		else
		{
			return List.of(this.fluid.copyWithAmount(CAPACITY));
		}

	}

	@Override
	public int getSize()
	{
		return 1;
	}

	@Override
	public boolean isEmpty()
	{
		return this.fluid.isEmpty();
	}

	@Override
	public FluidContainer copy()
	{
		return new CreativeFluidContainer(this.fluidFilter);
	}

	@Override
	public long getTankCapacity(int tankSlot)
	{
		return CAPACITY;
	}

	@Override
	public void fromContainer(FluidContainer container)
	{
		if (container.getSize() > 0)
		{
			this.fluid = container.getFluids().get(0).copyHolder();
		}
		else
		{
			this.fluid = FluidHolder.empty();
		}

	}

	@Override
	public long extractFromSlot(FluidHolder fluidHolder, FluidHolder toInsert, Runnable snapshot)
	{
		return 0;
	}

	@Override
	public boolean allowsInsertion()
	{
		return this.fluid.isEmpty();
	}

	@Override
	public boolean allowsExtraction()
	{
		return !this.fluid.isEmpty();
	}

	@Override
	public FluidSnapshot createSnapshot()
	{
		return new FluidSnapshot()
		{
			@Override
			public void loadSnapshot(FluidContainer container)
			{

			}
		};
	}

	@Override
	public void deserialize(CompoundTag root)
	{
		CompoundTag tag = root.getCompound(Botarium.BOTARIUM_DATA);
		ListTag fluids = tag.getList(SimpleFluidContainer.FLUID_KEY, Tag.TAG_COMPOUND);

		if (fluids.size() > 0)
		{
			CompoundTag fluid = fluids.getCompound(0);
			this.fluid = FluidHolder.fromCompound(fluid);
		}
		else
		{
			this.fluid = FluidHolder.empty();
		}

	}

	@Override
	public CompoundTag serialize(CompoundTag root)
	{
		CompoundTag tag = root.getCompound(Botarium.BOTARIUM_DATA);
		root.put(Botarium.BOTARIUM_DATA, tag);

		ListTag fluids = new ListTag();
		fluids.add(this.fluid.serialize());
		tag.put(SimpleFluidContainer.FLUID_KEY, fluids);

		return root;
	}

	@Override
	public void clearContent()
	{
		this.fluid = FluidHolder.empty();
	}

}
