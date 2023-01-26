package ad_astra_giselle_addon.common.network;

import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.IWorkingAreaBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class WorkingAreaVisibleMessage extends BlockPosMessage<WorkingAreaVisibleMessage>
{
	public static final ResourceLocation ID = AdAstraGiselleAddon.rl("working_visible");
	public static final Handler HANDLER = new Handler(WorkingAreaVisibleMessage::new);

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
	public ResourceLocation getID()
	{
		return ID;
	}

	@Override
	public PacketHandler<WorkingAreaVisibleMessage> getHandler()
	{
		return HANDLER;
	}

	public boolean isVisible()
	{
		return this.visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public static class Handler extends BlockPosMessage.Handler<WorkingAreaVisibleMessage>
	{
		public Handler(Supplier<WorkingAreaVisibleMessage> constructor)
		{
			super(constructor);
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
		public PacketContext handle(WorkingAreaVisibleMessage message)
		{
			return (player, level) ->
			{
				if (level.getBlockEntity(message.getBlockPos()) instanceof IWorkingAreaBlockEntity blockEntity)
				{
					blockEntity.setWorkingAreaVisible(message.isVisible());
				}
			};
		}

	}

}
