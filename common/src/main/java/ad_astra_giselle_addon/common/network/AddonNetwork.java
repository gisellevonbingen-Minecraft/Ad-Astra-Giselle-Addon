package ad_astra_giselle_addon.common.network;

import com.teamresourceful.resourcefullib.common.network.Network;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;

public class AddonNetwork
{
	public static final Network CHANNEL = new Network(AdAstraGiselleAddon.rl("main"), 0);

	public static void registerAll()
	{
		CHANNEL.register(ServerboundWorkingAreaVisibleMessage.TYPE);

		CHANNEL.register(ServerboundGravityNormalizerMessage.Length.TYPE);
		CHANNEL.register(ServerboundGravityNormalizerMessage.Offset.TYPE);

		CHANNEL.register(ServerboundRocketSensorMessage.SensingType.TYPE);
		CHANNEL.register(ServerboundRocketSensorMessage.Inverted.TYPE);
	}

}
