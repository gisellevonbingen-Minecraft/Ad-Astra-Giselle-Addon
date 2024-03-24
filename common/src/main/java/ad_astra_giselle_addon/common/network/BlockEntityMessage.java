package ad_astra_giselle_addon.common.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.base.PacketType;

import net.minecraft.resources.ResourceLocation;
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

	public static abstract class Type<T extends BlockEntityMessage<T, BLOCK_ENTITY>, BLOCK_ENTITY extends BlockEntity> extends BlockPosMessage.Type<T> implements PacketType<T>
	{
		public Type(Class<T> clazz, ResourceLocation id, Supplier<T> constructor)
		{
			super(clazz, id, constructor);
		}

		@SuppressWarnings("unchecked")
		public void consume(T message, Level level, Consumer<BLOCK_ENTITY> consumer)
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
				consumer.accept(blockEntity);
			}

		}

	}

}
