package ad_astra_giselle_addon.common.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.impl.transfer.item.InventoryStorageImpl;
import net.minecraft.core.Direction;

public class SidedInvWrapperSlot implements SingleSlotStorage<ItemVariant>
{
	public static CombinedStorage<ItemVariant, SingleSlotStorage<ItemVariant>> of(SidedItemContainerBlock containerHolder, Direction direction)
	{
		InventoryStorage impl = InventoryStorageImpl.of(containerHolder.getContainer(), direction);
		List<SingleSlotStorage<ItemVariant>> slots = new ArrayList<>();
		List<SingleSlotStorage<ItemVariant>> implSlots = impl.getSlots();

		for (int i = 0; i < implSlots.size(); i++)
		{
			slots.add(new SidedInvWrapperSlot(containerHolder, direction, implSlots.get(i), i));
		}

		return new CombinedStorage<>(Collections.unmodifiableList(slots));
	}

	private final SidedItemContainerBlock containerHolder;
	private final Direction direction;
	private final SingleSlotStorage<ItemVariant> impl;
	private final int slot;

	public SidedInvWrapperSlot(SidedItemContainerBlock containerHolder, Direction direction, SingleSlotStorage<ItemVariant> impl, int slot)
	{
		this.containerHolder = containerHolder;
		this.direction = direction;
		this.impl = impl;
		this.slot = slot;
	}

	private long clampAmount(long amount)
	{
		return Math.min(amount, this.containerHolder.getSideSlotLimit(this.slot, this.direction));
	}

	@Override
	public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction)
	{
		maxAmount = clampAmount(maxAmount);
		return this.impl.insert(resource, maxAmount, transaction);
	}

	@Override
	public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction)
	{
		return this.impl.extract(resource, maxAmount, transaction);
	}

	@Override
	public boolean isResourceBlank()
	{
		return this.impl.isResourceBlank();
	}

	@Override
	public ItemVariant getResource()
	{
		return this.impl.getResource();
	}

	@Override
	public long getAmount()
	{
		return this.impl.getAmount();
	}

	@Override
	public long getCapacity()
	{
		return this.impl.getCapacity();
	}

	@Override
	public StorageView<ItemVariant> getUnderlyingView()
	{
		return this.impl.getUnderlyingView();
	}

}
