package ad_astra_giselle_addon.common.util;

import net.minecraft.core.Vec3i;

public class Vec3iUtils
{
	public static Vec3i deriveX(Vec3i vec, int newX)
	{
		return new Vec3i(newX, vec.getY(), vec.getZ());
	}

	public static Vec3i deriveY(Vec3i vec, int newY)
	{
		return new Vec3i(vec.getZ(), newY, vec.getZ());
	}

	public static Vec3i deriveZ(Vec3i vec, int newZ)
	{
		return new Vec3i(vec.getZ(), vec.getY(), newZ);
	}

	private Vec3iUtils()
	{

	}

}
