package ad_astra_giselle_addon.common.network;

import com.teamresourceful.resourcefullib.common.network.Network;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;

public class AddonNetwork
{
	public static final Network CHANNEL = new Network(AdAstraGiselleAddon.rl("main"), 0);

	public static void registerAll()
	{
		CHANNEL.register(WorkingAreaVisibleMessage.TYPE);

		CHANNEL.register(GravityNormalizerMessage.Length.TYPE);
		CHANNEL.register(GravityNormalizerMessage.Offset.TYPE);

		CHANNEL.register(RocketSensorMessage.SensingType.TYPE);
		CHANNEL.register(RocketSensorMessage.Inverted.TYPE);
	}

}
