package ad_astra_giselle_addon.common.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class ServerboundBlockEntityMessage<T extends ServerboundBlockEntityMessage<T, BLOCK_ENTITY>, BLOCK_ENTITY extends BlockEntity> extends BlockPosMessage<T>
{
	public ServerboundBlockEntityMessage()
	{
		super();
	}

	public ServerboundBlockEntityMessage(BLOCK_ENTITY blockEntity)
	{
		super(blockEntity != null ? blockEntity.getBlockPos() : null);
	}

	public static abstract class Type<T extends ServerboundBlockEntityMessage<T, BLOCK_ENTITY>, BLOCK_ENTITY extends BlockEntity> extends BlockPosMessage.Type<T> implements ServerboundPacketType<T>
	{
		public Type(Supplier<T> constructor)
		{
			super(constructor);
		}

		@SuppressWarnings("unchecked")
		@Override
		public Consumer<Player> handle(T message)
		{
			return player ->
			{
				BLOCK_ENTITY blockEntity = null;

				try
				{
					BlockEntity original = player.level().getBlockEntity(message.getBlockPos());
					blockEntity = (BLOCK_ENTITY) original;
				}
				catch (Exception e)
				{

				}

				if (blockEntity != null)
				{
					this.onHandle(blockEntity, message, player);
				}

			};

		}

		protected abstract void onHandle(BLOCK_ENTITY blockEntity, T message, Player player);
	}

}
