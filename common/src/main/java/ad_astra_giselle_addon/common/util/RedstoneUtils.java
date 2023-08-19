package ad_astra_giselle_addon.common.util;

import net.minecraft.util.Mth;
import net.minecraft.world.level.redstone.Redstone;

public class RedstoneUtils
{
	public static int range(double ratio)
	{
		return Mth.ceil(Mth.clampedLerp(Redstone.SIGNAL_MIN, Redstone.SIGNAL_MAX, ratio));
	}

	public static int onOff(boolean value)
	{
		return value ? Redstone.SIGNAL_MAX : Redstone.SIGNAL_MIN;
	}

	private RedstoneUtils()
	{

	}

}
