package ad_astra_giselle_addon.common.network;

import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.IRocketSensingType;
import ad_astra_giselle_addon.common.block.entity.RocketSensingType;
import ad_astra_giselle_addon.common.block.entity.RocketSensorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class RocketSensorMessage<T extends BlockEntityMessage<T, RocketSensorBlockEntity>> extends BlockEntityMessage<T, RocketSensorBlockEntity>
{
	public RocketSensorMessage()
	{
		super();
	}

	public RocketSensorMessage(RocketSensorBlockEntity blockEntity)
	{
		super(blockEntity);
	}

	public static abstract class Handler<T extends BlockEntityMessage<T, RocketSensorBlockEntity>> extends BlockEntityMessage.Handler<T, RocketSensorBlockEntity>
	{
		public Handler(Supplier<T> constructor)
		{
			super(constructor);
		}

	}

	public static class SensingType extends RocketSensorMessage<SensingType>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("rocket_sensor_sensing_type");
		public static final Handler HANDLER = new Handler(SensingType::new);

		public static class Handler extends RocketSensorMessage.Handler<SensingType>
		{
			public Handler(Supplier<SensingType> constructor)
			{
				super(constructor);
			}

			@Override
			public void encode(SensingType message, FriendlyByteBuf buffer)
			{
				super.encode(message, buffer);
				buffer.writeResourceLocation(message.getSensingType().getName());
			}

			@Override
			public void decode(FriendlyByteBuf buffer, SensingType message)
			{
				super.decode(buffer, message);
				message.setSensingType(IRocketSensingType.find(buffer.readResourceLocation()));
			}

			@Override
			protected void onHandle(RocketSensorBlockEntity blockEntity, SensingType message, Player player, Level level)
			{
				blockEntity.setSensingType(message.getSensingType());
			}

		}

		private IRocketSensingType sensingType;

		public SensingType()
		{
			super();
			this.setSensingType(RocketSensingType.DISABLED);
		}

		public SensingType(RocketSensorBlockEntity blockEntity, IRocketSensingType sensingType)
		{
			super(blockEntity);
			this.setSensingType(sensingType);
		}

		public IRocketSensingType getSensingType()
		{
			return this.sensingType;
		}

		public void setSensingType(IRocketSensingType sensingType)
		{
			this.sensingType = sensingType;
		}

		@Override
		public ResourceLocation getID()
		{
			return ID;
		}

		@Override
		public PacketHandler<SensingType> getHandler()
		{
			return HANDLER;
		}

	}

	public static class Inverted extends RocketSensorMessage<Inverted>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("rocket_sensor_inverted");
		public static final Handler HANDLER = new Handler(Inverted::new);

		public static class Handler extends RocketSensorMessage.Handler<Inverted>
		{
			public Handler(Supplier<Inverted> constructor)
			{
				super(constructor);
			}

			@Override
			public void encode(Inverted message, FriendlyByteBuf buffer)
			{
				super.encode(message, buffer);
				buffer.writeBoolean(message.getInverted());
			}

			@Override
			public void decode(FriendlyByteBuf buffer, Inverted message)
			{
				super.decode(buffer, message);
				message.setInverted(buffer.readBoolean());
			}

			@Override
			protected void onHandle(RocketSensorBlockEntity blockEntity, Inverted message, Player player, Level level)
			{
				blockEntity.setInverted(message.getInverted());
			}

		}

		private boolean inverted;

		public Inverted()
		{
			super();
			this.setInverted(false);
		}

		public Inverted(RocketSensorBlockEntity blockEntity, boolean inverted)
		{
			super(blockEntity);
			this.setInverted(inverted);
		}

		public boolean getInverted()
		{
			return this.inverted;
		}

		public void setInverted(boolean inverted)
		{
			this.inverted = inverted;
		}

		@Override
		public ResourceLocation getID()
		{
			return ID;
		}

		@Override
		public PacketHandler<Inverted> getHandler()
		{
			return HANDLER;
		}

	}

}
