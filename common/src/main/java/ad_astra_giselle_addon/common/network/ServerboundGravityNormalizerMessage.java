package ad_astra_giselle_addon.common.network;

import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.base.PacketType;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import ad_astra_giselle_addon.common.util.Vec3iUtils;
import net.minecraft.core.Vec3i;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public abstract class ServerboundGravityNormalizerMessage<T extends ServerboundBlockEntityMessage<T, GravityNormalizerBlockEntity>> extends ServerboundBlockEntityMessage<T, GravityNormalizerBlockEntity>
{
	public ServerboundGravityNormalizerMessage()
	{
		super();
	}

	public ServerboundGravityNormalizerMessage(GravityNormalizerBlockEntity blockEntity)
	{
		super(blockEntity);
	}

	public static abstract class Type<T extends ServerboundBlockEntityMessage<T, GravityNormalizerBlockEntity>> extends ServerboundBlockEntityMessage.Type<T, GravityNormalizerBlockEntity>
	{
		public Type(Supplier<T> constructor)
		{
			super(constructor);
		}

	}

	public static abstract class Vector<T extends ServerboundBlockEntityMessage<T, GravityNormalizerBlockEntity>> extends ServerboundGravityNormalizerMessage<T>
	{
		private Vec3i vector;

		public static abstract class Type<T extends Vector<T>> extends ServerboundGravityNormalizerMessage.Type<T>
		{
			public Type(Supplier<T> constructor)
			{
				super(constructor);
			}

			@Override
			public void encode(T message, RegistryFriendlyByteBuf buffer)
			{
				super.encode(message, buffer);
				Vec3iUtils.write(buffer, message.getVector());
			}

			@Override
			public void decode(RegistryFriendlyByteBuf buffer, T message)
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
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("gracity_normalizer_length");
		public static final Vector.Type<Length> TYPE = new Vector.Type<>(Length::new)
		{
			@Override
			protected void onHandle(GravityNormalizerBlockEntity blockEntity, Length message, Player player)
			{
				blockEntity.setLength(message.getVector());
			}

			@Override
			public ResourceLocation id()
			{
				return ID;
			}
		};

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

	}

	public static class Offset extends Vector<Offset>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("gracity_normalizer_offset");
		public static final Vector.Type<Offset> TYPE = new Vector.Type<>(Offset::new)
		{
			@Override
			protected void onHandle(GravityNormalizerBlockEntity blockEntity, Offset message, Player player)
			{
				blockEntity.setOffset(message.getVector());
			}

			@Override
			public ResourceLocation id()
			{
				return ID;
			}
		};

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

	}

}
