package ad_astra_giselle_addon.common.network;

import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;

public abstract class BlockPosMessage<T extends BlockPosMessage<T>> implements Packet<T>
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

	public BlockPos getBlockPos()
	{
		return this.blockPos;
	}

	public void setBlockPos(BlockPos pos)
	{
		this.blockPos = pos;
	}

	public static abstract class Type<T extends BlockPosMessage<T>> implements PacketType<T>
	{
		private final Supplier<T> constructor;

		public Type(Supplier<T> constructor)
		{
			this.constructor = constructor;
		}

		@Override
		public void encode(T message, RegistryFriendlyByteBuf buffer)
		{
			BlockPos blockPos = message.getBlockPos();
			boolean notNull = blockPos != null;
			buffer.writeBoolean(notNull);

			if (notNull)
			{
				buffer.writeBlockPos(blockPos);
			}

		}

		@Override
		public final T decode(RegistryFriendlyByteBuf buffer)
		{
			T message = this.constructor.get();
			this.decode(buffer, message);
			return message;
		}

		public void decode(RegistryFriendlyByteBuf buffer, T message)
		{
			if (buffer.readBoolean())
			{
				message.setBlockPos(buffer.readBlockPos());
			}

		}

	}

}
