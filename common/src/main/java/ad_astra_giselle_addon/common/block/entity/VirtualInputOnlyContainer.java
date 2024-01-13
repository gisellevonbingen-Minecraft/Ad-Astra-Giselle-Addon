package ad_astra_giselle_addon.common.block.entity;

import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class VirtualInputOnlyContainer implements Container
{
	private final ContainerMachineBlockEntity blockEntity;
	private final int offset;
	private final int size;

	public VirtualInputOnlyContainer(ContainerMachineBlockEntity blockEntity, int offset, int size)
	{
		this.blockEntity = blockEntity;
		this.offset = offset;
		this.size = size;
	}

	public int toMachineSlot(int pSlot)
	{
		return pSlot + this.getOffset();
	}

	public int toRecipeSlot(int pSlot)
	{
		return pSlot - this.getOffset();
	}

	@Override
	public void clearContent()
	{

	}

	@Override
	public int getContainerSize()
	{
		return this.getSize();
	}

	@Override
	public boolean isEmpty()
	{
		int size = this.getSize();

		for (int i = 0; i < size; i++)
		{
			if (!this.getItem(i).isEmpty())
			{
				return false;
			}

		}

		return true;
	}

	@Override
	public ItemStack getItem(int pSlot)
	{
		return this.getBlockEntity().getItem(this.toMachineSlot(pSlot));
	}

	@Override
	public ItemStack removeItem(int pSlot, int pAmount)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeItemNoUpdate(int pSlot)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public void setItem(int pSlot, ItemStack pStack)
	{

	}

	@Override
	public boolean stillValid(Player pPlayer)
	{
		return true;
	}

	@Override
	public void setChanged()
	{

	}

	public final ContainerMachineBlockEntity getBlockEntity()
	{
		return this.blockEntity;
	}

	public final int getOffset()
	{
		return this.offset;
	}

	public final int getSize()
	{
		return this.size;
	}

}
