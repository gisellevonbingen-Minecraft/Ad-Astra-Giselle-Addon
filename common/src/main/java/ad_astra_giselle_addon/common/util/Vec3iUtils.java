package ad_astra_giselle_addon.common.util;

import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

public class Vec3iUtils
{
	public static Vec3i deriveX(Vec3i vec, int newX)
	{
		return new Vec3i(newX, vec.getY(), vec.getZ());
	}

	public static Vec3i deriveY(Vec3i vec, int newY)
	{
		return new Vec3i(vec.getX(), newY, vec.getZ());
	}

	public static Vec3i deriveZ(Vec3i vec, int newZ)
	{
		return new Vec3i(vec.getX(), vec.getY(), newZ);
	}

	public static Vec3i clamp(Vec3i vec, int min, int max)
	{
		int x = Mth.clamp(vec.getX(), min, max);
		int y = Mth.clamp(vec.getY(), min, max);
		int z = Mth.clamp(vec.getZ(), min, max);
		return new Vec3i(x, y, z);
	}

	public static Vec3i read(FriendlyByteBuf buffer)
	{
		int x = buffer.readInt();
		int y = buffer.readInt();
		int z = buffer.readInt();
		return new Vec3i(x, y, z);
	}

	public static void write(FriendlyByteBuf buffer, Vec3i vec)
	{
		buffer.writeInt(vec.getX());
		buffer.writeInt(vec.getY());
		buffer.writeInt(vec.getZ());
	}

	private Vec3iUtils()
	{

	}

}
