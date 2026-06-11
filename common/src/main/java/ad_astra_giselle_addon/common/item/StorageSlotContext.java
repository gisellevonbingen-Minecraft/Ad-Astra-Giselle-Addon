package ad_astra_giselle_addon.common.item;

import earth.terrarium.common_storage_lib.context.ItemContext;
import earth.terrarium.common_storage_lib.context.impl.IsolatedSlotContext;
import earth.terrarium.common_storage_lib.item.impl.noops.NoOpsItemContainer;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.item.ItemResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.storage.base.StorageSlot;
import earth.terrarium.common_storage_lib.storage.util.ModifiableItemSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StorageSlotContext implements ItemContext
{
	private final CommonStorage<ItemResource> outerContainer;
	private final StorageSlot<ItemResource> mainSlot;

	public static StorageSlotContext ofIsolated(ItemStack stack)
	{
		return ofContext(new IsolatedSlotContext(stack));
	}

	public static StorageSlotContext ofContext(ItemContext context)
	{
		return new StorageSlotContext(context.outerContainer(), context.mainSlot());
	}

	public static StorageSlotContext ofSlot(StorageSlot<ItemResource> mainSlot)
	{
		return ofSlot(NoOpsItemContainer.NO_OPS, mainSlot);
	}

	public static StorageSlotContext ofSlot(CommonStorage<ItemResource> outerContainer, StorageSlot<ItemResource> mainSlot)
	{
		return new StorageSlotContext(outerContainer, mainSlot);
	}

	private StorageSlotContext(CommonStorage<ItemResource> outerContainer, StorageSlot<ItemResource> mainSlot)
	{
		this.outerContainer = outerContainer;
		this.mainSlot = mainSlot;
	}

	public Item getItem()
	{
		return this.getResource().getItem();
	}

	public ItemStack getItemStack()
	{
		return ResourceStack.toItemStack(this.mainSlot.getContents());
	}

	public boolean update(ItemStack stack)
	{
		if (this.mainSlot instanceof ModifiableItemSlot modifiable)
		{
			modifiable.set(stack);
			return true;
		}

		return false;
	}

	@Override
	public CommonStorage<ItemResource> outerContainer()
	{
		return this.outerContainer;
	}

	@Override
	public StorageSlot<ItemResource> mainSlot()
	{
		return this.mainSlot;
	}

}
