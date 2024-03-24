package ad_astra_giselle_addon.common.capability;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.item.SidedItemContainerBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;

public class SidedInWrapperProvider implements ICapabilityProvider<BlockEntity, Direction, IItemHandler>
{
	private final SidedItemContainerBlock containerHolder;
	private final InvWrapper all;
	private final IItemHandlerModifiable[] sideds;

	public SidedInWrapperProvider(SidedItemContainerBlock containerHolder)
	{
		Direction[] directions = Direction.values();
		WorldlyContainer container = containerHolder.getContainer();

		this.containerHolder = containerHolder;
		this.all = new InvWrapper(container)
		{
			@Override
			public int getSlotLimit(int slot)
			{
				return SidedInWrapperProvider.this.getSlotLimit(slot, null);
			}
		};
		this.sideds = new IItemHandlerModifiable[directions.length];

		for (int x = 0; x < directions.length; x++)
		{
			final Direction side = directions[x];
			this.sideds[x] = new SidedInvWrapper(container, side)
			{
				@Override
				public int getSlotLimit(int slot)
				{
					return SidedInWrapperProvider.this.getSlotLimit(slot, this.side);
				}
			};

		}

	}

	public int getSlotLimit(int slot, @Nullable Direction direction)
	{
		return this.containerHolder.getSideSlotLimit(slot, direction);
	}

	@Override
	public @Nullable IItemHandler getCapability(BlockEntity object, Direction side)
	{
		if (side != null)
		{
			return this.sideds[side.ordinal()];
		}
		else
		{
			return this.all;
		}

	}

}
