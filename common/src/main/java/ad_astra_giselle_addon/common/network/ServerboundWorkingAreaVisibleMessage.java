package ad_astra_giselle_addon.common.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.IWorkingAreaBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ServerboundWorkingAreaVisibleMessage extends BlockPosMessage<ServerboundWorkingAreaVisibleMessage>
{
	public static final ResourceLocation ID = AdAstraGiselleAddon.rl("working_visible");
	public static final Type TYPE = new Type(ServerboundWorkingAreaVisibleMessage::new);

	private boolean visible;

	public ServerboundWorkingAreaVisibleMessage()
	{
		super();
		this.setVisible(false);
	}

	public ServerboundWorkingAreaVisibleMessage(BlockPos pos, boolean visible)
	{
		super(pos);
		this.setVisible(visible);
	}

	@Override
	public PacketType<ServerboundWorkingAreaVisibleMessage> type()
	{
		return TYPE;
	}

	public boolean isVisible()
	{
		return this.visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public static class Type extends BlockPosMessage.Type<ServerboundWorkingAreaVisibleMessage> implements ServerboundPacketType<ServerboundWorkingAreaVisibleMessage>
	{
		public Type(Supplier<ServerboundWorkingAreaVisibleMessage> constructor)
		{
			super(constructor);
		}

		@Override
		public void encode(ServerboundWorkingAreaVisibleMessage message, RegistryFriendlyByteBuf buffer)
		{
			super.encode(message, buffer);
			buffer.writeBoolean(message.isVisible());
		}

		@Override
		public void decode(RegistryFriendlyByteBuf buffer, ServerboundWorkingAreaVisibleMessage message)
		{
			super.decode(buffer, message);
			message.setVisible(buffer.readBoolean());
		}

		@Override
		public Consumer<Player> handle(ServerboundWorkingAreaVisibleMessage message)
		{
			return player ->
			{
				if (player.level().getBlockEntity(message.getBlockPos()) instanceof IWorkingAreaBlockEntity blockEntity)
				{
					blockEntity.setWorkingAreaVisible(message.isVisible());
				}
			};
		}

		@Override
		public ResourceLocation id()
		{
			return ID;
		}

	}

}
