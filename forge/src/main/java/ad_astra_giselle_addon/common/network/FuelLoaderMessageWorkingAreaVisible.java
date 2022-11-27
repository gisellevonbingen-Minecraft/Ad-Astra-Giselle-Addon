package ad_astra_giselle_addon.common.network;

import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class FuelLoaderMessageWorkingAreaVisible extends BlockEntityMessage<FuelLoaderBlockEntity>
{
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
	public void decode(FriendlyByteBuf buffer)
	{
		super.decode(buffer);
		this.setVisible(buffer.readBoolean());
	}

	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		super.encode(buffer);
		buffer.writeBoolean(this.isVisible());
	}

	@Override
	public void onHandle(FuelLoaderBlockEntity blockEntity, ServerPlayer sender)
	{
		blockEntity.setWorkingAreaVisible(this.isVisible());
	}

	public boolean isVisible()
	{
		return this.visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

}
