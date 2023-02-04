package ad_astra_giselle_addon.common.network;

import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import ad_astra_giselle_addon.common.util.Vec3iUtils;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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

	public static abstract class Handler<T extends BlockEntityMessage<T, GravityNormalizerBlockEntity>> extends BlockEntityMessage.Handler<T, GravityNormalizerBlockEntity>
	{
		public Handler(Supplier<T> constructor)
		{
			super(constructor);
		}

	}

	public static abstract class Vector<T extends BlockEntityMessage<T, GravityNormalizerBlockEntity>> extends GravityNormalizerMessage<T>
	{
		private Vec3i vector;

		public static abstract class Handler<T extends Vector<T>> extends GravityNormalizerMessage.Handler<T>
		{
			public Handler(Supplier<T> constructor)
			{
				super(constructor);
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
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("gracity_normalizer_length");
		public static final Vector.Handler<Length> HANDLER = new Vector.Handler<>(Length::new)
		{
			@Override
			protected void onHandle(GravityNormalizerBlockEntity blockEntity, Length message, Player player, Level level)
			{
				blockEntity.setLength(message.getVector());
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
		public ResourceLocation getID()
		{
			return ID;
		}

		@Override
		public PacketHandler<Length> getHandler()
		{
			return HANDLER;
		}

	}

	public static class Offset extends Vector<Offset>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("gracity_normalizer_offset");
		public static final Vector.Handler<Offset> HANDLER = new Vector.Handler<>(Offset::new)
		{
			@Override
			protected void onHandle(GravityNormalizerBlockEntity blockEntity, Offset message, Player player, Level level)
			{
				blockEntity.setOffset(message.getVector());
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
		public ResourceLocation getID()
		{
			return ID;
		}

		@Override
		public PacketHandler<Offset> getHandler()
		{
			return HANDLER;
		}

	}

}
