package ad_astra_giselle_addon.common.block.entity;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.item.SidedItemContainerBlock;
import earth.terrarium.ad_astra.common.block.machine.entity.AbstractMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AddonMachineBlockEntity extends AbstractMachineBlockEntity implements SidedItemContainerBlock
{
	public AddonMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState)
	{
		super(blockEntityType, blockPos, blockState);
	}

	@Override
	public void clearContent()
	{
		this.getItems().clear();
		this.setChanged();
	}

	@Override
	public void setItem(int pSlot, ItemStack pStack)
	{
		if (pStack.getCount() > this.getMaxStackSize())
		{
			pStack.setCount(this.getMaxStackSize());
		}

		this.getItems().set(pSlot, pStack);
		this.setChanged();
	}

	@Override
	public int getSideSlotLimit(int slot, @Nullable Direction directon)
	{
		return this.getMaxStackSize();
	}

	@Override
	public WorldlyContainer getContainer()
	{
		return this;
	}

}
