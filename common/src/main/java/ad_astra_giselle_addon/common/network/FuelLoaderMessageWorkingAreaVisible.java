package ad_astra_giselle_addon.common.network;

import java.util.function.Supplier;

import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FuelLoaderMessageWorkingAreaVisible extends BlockEntityMessage<FuelLoaderMessageWorkingAreaVisible, FuelLoaderBlockEntity>
{
	public static final ResourceLocation ID = AdAstraGiselleAddon.rl("fuel_loader_working_visible");
	public static final Handler HANDLER = new Handler(FuelLoaderMessageWorkingAreaVisible::new);

	private boolean visible;

	public FuelLoaderMessageWorkingAreaVisible()
	{
		super();
		this.setVisible(false);
	}

	public FuelLoaderMessageWorkingAreaVisible(FuelLoaderBlockEntity blockEntity, boolean visible)
	{
		super(blockEntity);
		this.setVisible(visible);
	}

	@Override
	public ResourceLocation getID()
	{
		return ID;
	}

	@Override
	public PacketHandler<FuelLoaderMessageWorkingAreaVisible> getHandler()
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

	public static class Handler extends BlockEntityMessage.Handler<FuelLoaderMessageWorkingAreaVisible, FuelLoaderBlockEntity>
	{
		public Handler(Supplier<FuelLoaderMessageWorkingAreaVisible> constructor)
		{
			super(constructor);
		}

		@Override
		public void encode(FuelLoaderMessageWorkingAreaVisible message, FriendlyByteBuf buffer)
		{
			super.encode(message, buffer);
			buffer.writeBoolean(message.isVisible());
		}

		@Override
		public void decode(FriendlyByteBuf buffer, FuelLoaderMessageWorkingAreaVisible message)
		{
			super.decode(buffer, message);
			message.setVisible(buffer.readBoolean());
		}

		@Override
		protected void onHandle(FuelLoaderBlockEntity blockEntity, FuelLoaderMessageWorkingAreaVisible message, Player player, Level level)
		{
			blockEntity.setWorkingAreaVisible(message.isVisible());
		}

	}

}
