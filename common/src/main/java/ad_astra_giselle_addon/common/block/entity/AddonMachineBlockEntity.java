package ad_astra_giselle_addon.common.block.entity;

import earth.terrarium.ad_astra.common.block.machine.entity.AbstractMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AddonMachineBlockEntity extends AbstractMachineBlockEntity
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
		ItemStack prev = this.getItem(pSlot);

		if (!ItemStack.matches(prev, pStack) || prev.getCount() != pStack.getCount())
		{
			this.getItems().set(pSlot, pStack);

			if (pStack.getCount() > this.getMaxStackSize())
			{
				pStack.setCount(this.getMaxStackSize());
			}

			this.setChanged();
		}

	}

}
