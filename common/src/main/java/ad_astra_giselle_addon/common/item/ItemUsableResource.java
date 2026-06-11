package ad_astra_giselle_addon.common.item;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.common_storage_lib.energy.EnergyApi;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public enum ItemUsableResource
{
	Energy()
		{
			@Override
			public boolean test(StorageSlotContext context)
			{
				return context.isPresent(EnergyApi.ITEM);
			}

			@Override
			public long extract(Player living, EquipmentSlot slot, StorageSlotContext context, long amount, boolean simulate)
			{
				ValueStorage energyContainer = context.find(EnergyApi.ITEM);
				return energyContainer != null ? energyContainer.extract(amount, simulate) : 0L;
			}

		},
	Durability()
		{
			@Override
			public boolean test(StorageSlotContext context)
			{
				return true;
			}

			@Override
			public long extract(Player living, EquipmentSlot slot, StorageSlotContext context, long amount, boolean simulate)
			{
				if (!simulate)
				{
					ItemStack stack = context.getItemStack();
					stack.hurtAndBreak((int) amount, living, slot);
					context.update(stack);
				}

				return amount;
			}

		},
	// EOL
	;

	@Nullable
	public static ItemUsableResource first(StorageSlotContext context)
	{
		for (ItemUsableResource resource : ItemUsableResource.values())
		{
			if (resource.test(context))
			{
				return resource;
			}

		}

		return null;
	}

	private ItemUsableResource()
	{

	}

	public abstract boolean test(StorageSlotContext context);

	public abstract long extract(Player living, EquipmentSlot slot, StorageSlotContext context, long amount, boolean simulate);
}
