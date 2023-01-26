package ad_astra_giselle_addon.common.network;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;

public class AddonNetwork
{
	public static final NetworkChannel CHANNEL = new NetworkChannel(AdAstraGiselleAddon.MOD_ID, 0, "main");

	public static void registerAll()
	{
		CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, WorkingAreaVisibleMessage.ID, WorkingAreaVisibleMessage.HANDLER, WorkingAreaVisibleMessage.class);
	}

}
