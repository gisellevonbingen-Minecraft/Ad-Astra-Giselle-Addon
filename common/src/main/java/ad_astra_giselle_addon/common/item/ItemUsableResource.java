package ad_astra_giselle_addon.common.item;

import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public enum ItemUsableResource
{
	Energy()
		{
			@Override
			public boolean test(ItemStack item)
			{
				return EnergyApi.isEnergyItem(item);
			}

			@Override
			public long extract(LivingEntity living, EquipmentSlot slot, ItemStackReference item, long amount, boolean simulate)
			{
				return this.extract(item, amount, simulate);
			}

			private Long extract(ItemStackReference item, long amount, boolean simulate)
			{
				EnergyContainer energyContainer = EnergyContainer.of(item);
				return energyContainer != null ? energyContainer.extractEnergy(amount, simulate) : 0L;
			}

		},
	Durability()
		{
			@Override
			public boolean test(ItemStack item)
			{
				return true;
			}

			@Override
			public long extract(LivingEntity living, EquipmentSlot slot, ItemStackReference item, long amount, boolean simulate)
			{
				return extract(living, item, amount, simulate, l -> l.broadcastBreakEvent(slot));
			}

			private long extract(LivingEntity living, ItemStackReference item, long amount, boolean simulate, Consumer<LivingEntity> onBreak)
			{
				ItemStack stack = item.getStack();

				if (stack.isDamageableItem())
				{
					if (!simulate)
					{
						stack.hurtAndBreak((int) amount, living, onBreak);
						item.setStack(stack);
					}

				}

				return amount;
			}
		},
	// EOL
	;

	@Nullable
	public static ItemUsableResource first(ItemStack item)
	{
		for (ItemUsableResource resource : ItemUsableResource.values())
		{
			if (resource.test(item))
			{
				return resource;
			}

		}

		return null;
	}

	private ItemUsableResource()
	{

	}

	public abstract boolean test(ItemStack item);

	public abstract long extract(LivingEntity living, EquipmentSlot slot, ItemStackReference item, long amount, boolean simulate);
}
