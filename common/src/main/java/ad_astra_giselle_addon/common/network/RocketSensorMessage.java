package ad_astra_giselle_addon.common.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.IRocketSensingType;
import ad_astra_giselle_addon.common.block.entity.RocketSensingType;
import ad_astra_giselle_addon.common.block.entity.RocketSensorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

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

	public static abstract class Type<T extends BlockEntityMessage<T, RocketSensorBlockEntity>> extends BlockEntityMessage.Type<T, RocketSensorBlockEntity>
	{
		public Type(Class<T> clazz, ResourceLocation id, Supplier<T> constructor)
		{
			super(clazz, id, constructor);
		}

	}

	public static class SensingType extends RocketSensorMessage<SensingType>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("rocket_sensor_sensing_type");
		public static final Type TYPE = new Type(SensingType.class, ID, SensingType::new);

		public static class Type extends RocketSensorMessage.Type<SensingType> implements ServerboundPacketType<SensingType>
		{
			public Type(Class<SensingType> clazz, ResourceLocation id, Supplier<SensingType> constructor)
			{
				super(clazz, id, constructor);
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
			public Consumer<Player> handle(SensingType message)
			{
				return player -> this.consume(message, player.level(), blockEntity -> blockEntity.setSensingType(message.getSensingType()));
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
		public PacketType<SensingType> type()
		{
			return TYPE;
		}

	}

	public static class Inverted extends RocketSensorMessage<Inverted>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("rocket_sensor_inverted");
		public static final Type TYPE = new Type(Inverted.class, ID, Inverted::new);

		public static class Type extends RocketSensorMessage.Type<Inverted> implements ServerboundPacketType<Inverted>
		{
			public Type(Class<Inverted> clazz, ResourceLocation id, Supplier<Inverted> constructor)
			{
				super(clazz, id, constructor);
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
			public Consumer<Player> handle(Inverted message)
			{
				return player -> this.consume(message, player.level(), blockEntity -> blockEntity.setInverted(message.getInverted()));
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
		public PacketType<Inverted> type()
		{
			return TYPE;
		}

	}

}
