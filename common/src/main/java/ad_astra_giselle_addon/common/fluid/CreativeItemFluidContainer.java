package ad_astra_giselle_addon.common.fluid;

import java.util.List;
import java.util.function.BiPredicate;

import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.api.fluid.FluidContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.FluidSnapshot;
import earth.terrarium.botarium.api.fluid.ItemFilteredFluidContainer;
import earth.terrarium.botarium.api.fluid.ItemFluidContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;

public class CreativeItemFluidContainer implements ItemFluidContainer
{
	public static final Long CAPACITY = (long) Integer.MAX_VALUE;

	private final ItemStack stack;
	private final BiPredicate<Integer, FluidHolder> fluidFilter;
	private FluidHolder fluid;

	public CreativeItemFluidContainer(ItemStack stack, BiPredicate<Integer, FluidHolder> fluidFilter)
	{
		this.stack = stack;
		this.fluidFilter = fluidFilter;
		this.deserialize(stack.getOrCreateTagElement(Botarium.BOTARIUM_DATA));
	}

	@Override
	public void update(ItemStack itemStack)
	{
		this.serialize(itemStack.getOrCreateTagElement(Botarium.BOTARIUM_DATA));
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
			return FluidHooks.emptyFluid();
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
			return List.of(FluidHooks.emptyFluid());
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
		return new CreativeItemFluidContainer(this.stack.copy(), this.fluidFilter);
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
			this.fluid = FluidHooks.emptyFluid();
		}

	}

	@Override
	public long extractFromSlot(FluidHolder fluidHolder, FluidHolder toInsert, Runnable snapshot)
	{
		if (fluidHolder.matches(toInsert))
		{
			long extracted = Mth.clamp(toInsert.getFluidAmount(), 0, fluidHolder.getFluidAmount());
			snapshot.run();
			fluidHolder.setAmount(fluidHolder.getFluidAmount() - extracted);
			if (fluidHolder.getFluidAmount() == 0)
			{
				fluidHolder.setFluid(Fluids.EMPTY);
			}
			return extracted;
		}
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
	public void deserialize(CompoundTag tag)
	{
		ListTag fluids = tag.getList(ItemFilteredFluidContainer.FLUID_KEY, Tag.TAG_COMPOUND);

		if (fluids.size() > 0)
		{
			CompoundTag fluid = fluids.getCompound(0);
			this.fluid = FluidHooks.fluidFromCompound(fluid);
		}
		else
		{
			this.fluid = FluidHooks.emptyFluid();
		}

	}

	@Override
	public CompoundTag serialize(CompoundTag tag)
	{
		ListTag fluids = new ListTag();
		fluids.add(this.fluid.serialize());
		tag.put(ItemFilteredFluidContainer.FLUID_KEY, fluids);

		return tag;
	}

	@Override
	public ItemStack getContainerItem()
	{
		return this.stack;
	}

}
