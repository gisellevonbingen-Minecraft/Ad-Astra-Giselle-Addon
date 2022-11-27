package ad_astra_giselle_addon.common.network;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class BlockPosMessage extends AbstractMessage
{
	private BlockPos blockPos;

	public BlockPosMessage()
	{
		this.setBlockPos(null);
	}

	public BlockPosMessage(BlockPos pos)
	{
		this.setBlockPos(pos);
	}

	@Override
	public void decode(FriendlyByteBuf buffer)
	{
		if (buffer.readBoolean())
		{
			this.setBlockPos(buffer.readBlockPos());
		}

	}

	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		BlockPos blockPos = this.getBlockPos();
		boolean notNull = blockPos != null;
		buffer.writeBoolean(notNull);

		if (notNull)
		{
			buffer.writeBlockPos(blockPos);
		}

	}

	@Override
	public void onHandle(NetworkEvent.Context context)
	{
		BlockPos blockPos = this.getBlockPos();

		if (blockPos != null)
		{
			this.onHandle(blockPos, context.getSender());
		}

	}

	public void onHandle(BlockPos blockPos, @Nullable ServerPlayer sender)
	{

	}

	public BlockPos getBlockPos()
	{
		return this.blockPos;
	}

	public void setBlockPos(BlockPos pos)
	{
		this.blockPos = pos;
	}

}
