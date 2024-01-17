package ad_astra_giselle_addon.common.item;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.botarium.api.energy.EnergyHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.item.ItemStack;

public enum ItemUsableResource
{
	Energy()
		{
			@Override
			public boolean test(ItemStack item)
			{
				return EnergyHooks.isEnergyItem(item);
			}

			@Override
			public long extract(ItemStackHolder item, long amount, boolean simulate)
			{
				return EnergyHooks.safeGetItemEnergyManager(item.getStack()).map(e -> e.extract(item, amount, simulate)).orElse(0L);
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
			public long extract(ItemStackHolder item, long amount, boolean simulate)
			{
				ItemStack stack = item.getStack();

				if (stack.isDamageableItem())
				{
					int prevDamage = stack.getDamageValue();
					int maxDamage = stack.getMaxDamage();
					int damaging = Math.min(maxDamage - prevDamage, (int) amount);
					int nextDamage = prevDamage + damaging;

					if (nextDamage < maxDamage)
					{
						if (!simulate)
						{
							stack.setDamageValue(nextDamage);
							item.setStack(stack);
						}

						return damaging;
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

	public abstract long extract(ItemStackHolder item, long amount, boolean simulate);

}
