package ad_astra_giselle_addon.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public abstract class AbstractMessage
{
	public abstract void decode(FriendlyByteBuf buffer);

	public abstract void encode(FriendlyByteBuf buffer);

	public abstract void onHandle(NetworkEvent.Context context);

}
