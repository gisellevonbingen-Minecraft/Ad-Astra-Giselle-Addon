package ad_astra_giselle_addon.common.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import ad_astra_giselle_addon.common.util.Vec3iUtils;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public abstract class GravityNormalizerMessage<T extends BlockEntityMessage<T, GravityNormalizerBlockEntity>> extends BlockEntityMessage<T, GravityNormalizerBlockEntity>
{
	public GravityNormalizerMessage()
	{
		super();
	}

	public GravityNormalizerMessage(GravityNormalizerBlockEntity blockEntity)
	{
		super(blockEntity);
	}

	public static abstract class Type<T extends BlockEntityMessage<T, GravityNormalizerBlockEntity>> extends BlockEntityMessage.Type<T, GravityNormalizerBlockEntity>
	{
		public Type(Class<T> clazz, ResourceLocation id, Supplier<T> constructor)
		{
			super(clazz, id, constructor);
		}

	}

	public static abstract class Vector<T extends BlockEntityMessage<T, GravityNormalizerBlockEntity>> extends GravityNormalizerMessage<T>
	{
		private Vec3i vector;

		public static abstract class Type<T extends Vector<T>> extends GravityNormalizerMessage.Type<T>
		{
			public Type(Class<T> clazz, ResourceLocation id, Supplier<T> constructor)
			{
				super(clazz, id, constructor);
			}

			@Override
			public void encode(T message, FriendlyByteBuf buffer)
			{
				super.encode(message, buffer);
				Vec3iUtils.write(buffer, message.getVector());
			}

			@Override
			public void decode(FriendlyByteBuf buffer, T message)
			{
				super.decode(buffer, message);
				message.setVector(Vec3iUtils.read(buffer));
			}

		}

		public Vector()
		{
			super();
			this.setVector(Vec3i.ZERO);
		}

		public Vector(GravityNormalizerBlockEntity blockEntity, Vec3i vector)
		{
			super(blockEntity);
			this.setVector(vector);
		}

		public Vec3i getVector()
		{
			return this.vector;
		}

		public void setVector(Vec3i vector)
		{
			this.vector = vector;
		}

	}

	public static class Length extends Vector<Length>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("gravity_normalizer_length");
		public static final Type TYPE = new Type(Length.class, ID, Length::new);

		public Length()
		{
			super();
		}

		public Length(GravityNormalizerBlockEntity blockEntity, Vec3i length)
		{
			super(blockEntity, length);
		}

		@Override
		public PacketType<Length> type()
		{
			return TYPE;
		}

		public static class Type extends Vector.Type<Length> implements ServerboundPacketType<Length>
		{
			public Type(Class<Length> clazz, ResourceLocation id, Supplier<Length> constructor)
			{
				super(clazz, id, constructor);
			}

			@Override
			public Consumer<Player> handle(Length message)
			{
				return player -> this.consume(message, player.level(), blockEntity -> blockEntity.setLength(message.getVector()));
			}

		}

	}

	public static class Offset extends Vector<Offset>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("gravity_normalizer_offset");
		public static final Type TYPE = new Type(Offset.class, ID, Offset::new);

		public Offset()
		{
			super();
		}

		public Offset(GravityNormalizerBlockEntity blockEntity, Vec3i offset)
		{
			super(blockEntity, offset);
		}

		@Override
		public PacketType<Offset> type()
		{
			return TYPE;
		}

		public static class Type extends Vector.Type<Offset> implements ServerboundPacketType<Offset>
		{
			public Type(Class<Offset> clazz, ResourceLocation id, Supplier<Offset> constructor)
			{
				super(clazz, id, constructor);
			}

			@Override
			public Consumer<Player> handle(Offset message)
			{
				return player -> this.consume(message, player.level(), blockEntity -> blockEntity.setOffset(message.getVector()));
			}

		}

	}

}
