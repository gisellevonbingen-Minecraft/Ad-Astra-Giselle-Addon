package ad_astra_giselle_addon.common.capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.item.SidedItemContainerBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class SidedInWrapperProvider implements ICapabilityProvider
{
	private final SidedItemContainerBlock containerHolder;
	private final LazyOptional<InvWrapper> all;
	private final LazyOptional<IItemHandlerModifiable>[] sideds;

	@SuppressWarnings("unchecked")
	public SidedInWrapperProvider(SidedItemContainerBlock containerHolder)
	{
		Direction[] directions = Direction.values();
		WorldlyContainer container = containerHolder.getContainer();

		this.containerHolder = containerHolder;
		this.all = LazyOptional.of(() -> new InvWrapper(container)
		{
			@Override
			public int getSlotLimit(int slot)
			{
				return SidedInWrapperProvider.this.getSlotLimit(slot, null);
			};
		});
		this.sideds = new LazyOptional[directions.length];

		for (int x = 0; x < directions.length; x++)
		{
			final Direction side = directions[x];
			this.sideds[x] = LazyOptional.of(() -> new SidedInvWrapper(container, side)
			{
				@Override
				public int getSlotLimit(int slot)
				{
					return SidedInWrapperProvider.this.getSlotLimit(slot, this.side);
				};
			});

		}

	}

	public int getSlotLimit(int slot, @Nullable Direction direction)
	{
		return this.containerHolder.getSideSlotLimit(slot, direction);
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if (cap == ForgeCapabilities.ITEM_HANDLER)
		{
			if (side != null)
			{
				return this.sideds[side.ordinal()].cast();
			}
			else
			{
				return this.all.cast();
			}

		}

		return LazyOptional.empty();
	}

}
