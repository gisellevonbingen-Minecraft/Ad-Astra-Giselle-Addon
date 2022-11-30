package ad_astra_giselle_addon.common.network;

import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class BlockEntityMessage<T extends BlockEntityMessage<T, BLOCK_ENTITY>, BLOCK_ENTITY extends BlockEntity> extends BlockPosMessage<T>
{
	public BlockEntityMessage()
	{
		super();
	}

	public BlockEntityMessage(BLOCK_ENTITY blockEntity)
	{
		super(blockEntity != null ? blockEntity.getBlockPos() : null);
	}

	public static abstract class Handler<T extends BlockEntityMessage<T, BLOCK_ENTITY>, BLOCK_ENTITY extends BlockEntity> extends BlockPosMessage.Handler<T>
	{
		public Handler(Supplier<T> constructor)
		{
			super(constructor);
		}

		@SuppressWarnings("unchecked")
		@Override
		public PacketContext handle(T message)
		{
			return (player, level) ->
			{
				BLOCK_ENTITY blockEntity = null;

				try
				{
					BlockEntity original = level.getBlockEntity(message.getBlockPos());
					blockEntity = (BLOCK_ENTITY) original;
				}
				catch (Exception e)
				{

				}

				if (blockEntity != null)
				{
					this.onHandle(blockEntity, message, player, level);
				}

			};

		}

		protected abstract void onHandle(BLOCK_ENTITY blockEntity, T message, Player player, Level level);
	}

}
