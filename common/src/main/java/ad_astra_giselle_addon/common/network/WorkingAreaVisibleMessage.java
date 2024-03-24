package ad_astra_giselle_addon.common.network;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.IWorkingAreaBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class WorkingAreaVisibleMessage extends BlockPosMessage<WorkingAreaVisibleMessage>
{
	public static final ResourceLocation ID = AdAstraGiselleAddon.rl("working_visible");
	public static final Type TYPE = new Type(WorkingAreaVisibleMessage.class, ID, WorkingAreaVisibleMessage::new);

	private boolean visible;

	public WorkingAreaVisibleMessage()
	{
		super();
		this.setVisible(false);
	}

	public WorkingAreaVisibleMessage(BlockPos pos, boolean visible)
	{
		super(pos);
		this.setVisible(visible);
	}

	@Override
	public PacketType<WorkingAreaVisibleMessage> type()
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

	public static class Type extends BlockPosMessage.Type<WorkingAreaVisibleMessage> implements ServerboundPacketType<WorkingAreaVisibleMessage>
	{
		public Type(Class<WorkingAreaVisibleMessage> clazz, ResourceLocation id, Supplier<WorkingAreaVisibleMessage> constructor)
		{
			super(clazz, id, constructor);
		}

		@Override
		public void encode(WorkingAreaVisibleMessage message, FriendlyByteBuf buffer)
		{
			super.encode(message, buffer);
			buffer.writeBoolean(message.isVisible());
		}

		@Override
		public void decode(FriendlyByteBuf buffer, WorkingAreaVisibleMessage message)
		{
			super.decode(buffer, message);
			message.setVisible(buffer.readBoolean());
		}

		@Override
		public Consumer<Player> handle(WorkingAreaVisibleMessage message)
		{
			return player ->
			{
				if (player.level().getBlockEntity(message.getBlockPos()) instanceof IWorkingAreaBlockEntity blockEntity)
				{
					blockEntity.setWorkingAreaVisible(message.isVisible());
				}
			};
		}

	}

}
