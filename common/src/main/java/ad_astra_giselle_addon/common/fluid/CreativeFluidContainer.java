package ad_astra_giselle_addon.common.fluid;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import earth.terrarium.common_storage_lib.context.ItemContext;
import earth.terrarium.common_storage_lib.fluid.util.FluidStorageData;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.base.StorageSlot;
import earth.terrarium.common_storage_lib.storage.base.UpdateManager;
import earth.terrarium.common_storage_lib.storage.util.TransferUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;

public class CreativeFluidContainer implements CommonStorage<FluidResource>, UpdateManager<FluidStorageData>
{
	public static final Long CAPACITY = (long) Integer.MAX_VALUE;

	protected final NonNullList<Slot> slots;
	private final Runnable update;
	private final Runnable save;

	public CreativeFluidContainer(ItemContext context, DataComponentType<FluidStorageData> componentType, Predicate<FluidResource> fluidFilter)
	{
		this.update = context::updateAll;
		this.save = () -> context.set(componentType, FluidStorageData.from(this));
		this.slots = NonNullList.withSize(1, new Slot(this::update, this.save, fluidFilter));

		FluidStorageData data = context.getResource().get(componentType);

		if (data != null)
		{
			readSnapshot(data);
		}

	}

	@Override
	public int size()
	{
		return slots.size();
	}

	@Override
	public @NotNull StorageSlot<FluidResource> get(int index)
	{
		return slots.get(index);
	}

	@Override
	public FluidStorageData createSnapshot()
	{
		return FluidStorageData.from(this);
	}

	@Override
	public void readSnapshot(FluidStorageData snapshot)
	{
		for (int i = 0; i < this.slots.size() && i < snapshot.stacks().size(); i++)
		{
			this.slots.get(i).readSnapshot(snapshot.stacks().get(i));
		}

	}

	@Override
	public void update()
	{
		this.update.run();
	}

	@Override
	public long insert(FluidResource resource, long amount, boolean simulate)
	{
		return TransferUtil.insertSlots(this, resource, amount, simulate);
	}

	@Override
	public long extract(FluidResource resource, long amount, boolean simulate)
	{
		return TransferUtil.extractSlots(this, resource, amount, simulate);
	}

	public static class Slot implements StorageSlot<FluidResource>, UpdateManager<ResourceStack<FluidResource>>
	{
		private final Runnable update;
		private final Runnable save;
		private final Predicate<FluidResource> fluidFilter;

		private FluidResource resource = FluidResource.BLANK;

		public Slot(Runnable update, Runnable save, Predicate<FluidResource> fluidFilter)
		{
			this.update = update;
			this.save = save;
			this.fluidFilter = fluidFilter;
		}

		@Override
		public long insert(FluidResource resource, long amount, boolean simulate)
		{
			if (this.getResource().isBlank() && this.fluidFilter.test(resource))
			{
				if (!simulate)
				{
					this.save.run();
				}

				return amount;
			}
			else
			{
				return 0;
			}

		}

		@Override
		public long extract(FluidResource resource, long amount, boolean simulate)
		{
			FluidResource holding = this.getResource();

			if (holding.isBlank() || !this.fluidFilter.test(resource) || !holding.equals(resource))
			{
				return 0;
			}
			else
			{
				if (!simulate)
				{
					this.save.run();
				}

				return amount;
			}

		}

		@Override
		public boolean isResourceValid(FluidResource resource)
		{
			return this.fluidFilter.test(resource);
		}

		@Override
		public FluidResource getResource()
		{
			return this.resource;
		}

		@Override
		public long getAmount()
		{
			return this.resource.isBlank() ? 0 : CAPACITY;
		}

		@Override
		public long getLimit(FluidResource resource)
		{
			return CAPACITY;
		}

		@Override
		public ResourceStack<FluidResource> createSnapshot()
		{
			return this.resource.toStack(this.getAmount());
		}

		@Override
		public void readSnapshot(ResourceStack<FluidResource> snapshot)
		{
			this.resource = snapshot.resource();
		}

		@Override
		public void update()
		{
			this.update.run();
		}

	}

}
