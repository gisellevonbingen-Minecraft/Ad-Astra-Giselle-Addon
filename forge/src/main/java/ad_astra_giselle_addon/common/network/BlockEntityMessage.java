package ad_astra_giselle_addon.common.network;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BlockEntityMessage<T extends BlockEntity> extends BlockPosMessage
{
	public BlockEntityMessage()
	{
		super();
	}

	public BlockEntityMessage(T blockEntity)
	{
		super(blockEntity != null ? blockEntity.getBlockPos() : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onHandle(BlockPos blockPos, @Nullable ServerPlayer sender)
	{
		super.onHandle(blockPos, sender);

		if (sender == null)
		{
			return;
		}

		T blockEntity = null;

		try
		{
			BlockEntity original = sender.getLevel().getBlockEntity(blockPos);
			blockEntity = (T) original;
		}
		catch (Exception e)
		{

		}

		if (blockEntity != null)
		{
			this.onHandle(blockEntity, sender);
		}

	}

	public abstract void onHandle(T tileEntity, ServerPlayer sender);

}
