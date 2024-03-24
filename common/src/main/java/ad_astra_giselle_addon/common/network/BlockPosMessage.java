package ad_astra_giselle_addon.common.network;

import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.AbstractPacketType;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

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

	public static abstract class Type<T extends BlockPosMessage<T>> extends AbstractPacketType<T> implements PacketType<T>
	{
		private final Supplier<T> constructor;

		public Type(Class<T> clazz, ResourceLocation id, Supplier<T> constructor)
		{
			super(clazz, id);
			this.constructor = constructor;
		}

		@Override
		public void encode(T message, FriendlyByteBuf buffer)
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
		public final T decode(FriendlyByteBuf buffer)
		{
			T message = this.constructor.get();
			this.decode(buffer, message);
			return message;
		}

		public void decode(FriendlyByteBuf buffer, T message)
		{
			if (buffer.readBoolean())
			{
				message.setBlockPos(buffer.readBlockPos());
			}

		}

	}

}
