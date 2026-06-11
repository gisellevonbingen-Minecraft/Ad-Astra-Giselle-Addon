package ad_astra_giselle_addon.common.network;

import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.base.PacketType;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.IRocketSensingType;
import ad_astra_giselle_addon.common.block.entity.RocketSensingType;
import ad_astra_giselle_addon.common.block.entity.RocketSensorBlockEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public abstract class ServerboundRocketSensorMessage<T extends ServerboundBlockEntityMessage<T, RocketSensorBlockEntity>> extends ServerboundBlockEntityMessage<T, RocketSensorBlockEntity>
{
	public ServerboundRocketSensorMessage()
	{
		super();
	}

	public ServerboundRocketSensorMessage(RocketSensorBlockEntity blockEntity)
	{
		super(blockEntity);
	}

	public static abstract class Type<T extends ServerboundBlockEntityMessage<T, RocketSensorBlockEntity>> extends ServerboundBlockEntityMessage.Type<T, RocketSensorBlockEntity>
	{
		public Type(Supplier<T> constructor)
		{
			super(constructor);
		}

	}

	public static class SensingType extends ServerboundRocketSensorMessage<SensingType>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("rocket_sensor_sensing_type");
		public static final Type TYPE = new Type(SensingType::new);

		public static class Type extends ServerboundRocketSensorMessage.Type<SensingType>
		{
			public Type(Supplier<SensingType> constructor)
			{
				super(constructor);
			}

			@Override
			public void encode(SensingType message, RegistryFriendlyByteBuf buffer)
			{
				super.encode(message, buffer);
				buffer.writeResourceLocation(message.getSensingType().getName());
			}

			@Override
			public void decode(RegistryFriendlyByteBuf buffer, SensingType message)
			{
				super.decode(buffer, message);
				message.setSensingType(IRocketSensingType.find(buffer.readResourceLocation()));
			}

			@Override
			protected void onHandle(RocketSensorBlockEntity blockEntity, SensingType message, Player player)
			{
				blockEntity.setSensingType(message.getSensingType());
			}

			@Override
			public ResourceLocation id()
			{
				return ID;
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

	public static class Inverted extends ServerboundRocketSensorMessage<Inverted>
	{
		public static final ResourceLocation ID = AdAstraGiselleAddon.rl("rocket_sensor_inverted");
		public static final Type TYPE = new Type(Inverted::new);

		public static class Type extends ServerboundRocketSensorMessage.Type<Inverted>
		{
			public Type(Supplier<Inverted> constructor)
			{
				super(constructor);
			}

			@Override
			public void encode(Inverted message, RegistryFriendlyByteBuf buffer)
			{
				super.encode(message, buffer);
				buffer.writeBoolean(message.getInverted());
			}

			@Override
			public void decode(RegistryFriendlyByteBuf buffer, Inverted message)
			{
				super.decode(buffer, message);
				message.setInverted(buffer.readBoolean());
			}

			@Override
			protected void onHandle(RocketSensorBlockEntity blockEntity, Inverted message, Player player)
			{
				blockEntity.setInverted(message.getInverted());
			}

			@Override
			public ResourceLocation id()
			{
				return ID;
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
