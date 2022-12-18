package ad_astra_giselle_addon.common.block.entity;

import earth.terrarium.ad_astra.common.block.machine.entity.AbstractMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AddonMachineBlockEntity extends AbstractMachineBlockEntity
{
	public AddonMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState)
	{
		super(blockEntityType, blockPos, blockState);
	}

	protected void onInventoryChanged()
	{

	}

	@Override
	public void clearContent()
	{
		this.getItems().clear();
		this.onInventoryChanged();
	}

	@Override
	public ItemStack removeItem(int pSlot, int pAmount)
	{
		ItemStack removed = ContainerHelper.removeItem(this.getItems(), pSlot, pAmount);

		if (!removed.isEmpty())
		{
			this.onInventoryChanged();
		}

		return removed;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot)
	{
		ItemStack removed = super.removeItemNoUpdate(slot);

		if (!removed.isEmpty())
		{
			this.onInventoryChanged();
		}

		return removed;
	}

	@Override
	public void setItem(int pSlot, ItemStack pStack)
	{
		this.getItems().set(pSlot, pStack);

		if (pStack.getCount() > this.getMaxStackSize())
		{
			pStack.setCount(this.getMaxStackSize());
		}

		this.onInventoryChanged();
	}

}
