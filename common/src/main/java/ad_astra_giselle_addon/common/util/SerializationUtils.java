package ad_astra_giselle_addon.common.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;

import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

public class SerializationUtils
{
	public static <FROM, TO> TO read(Codec<TO> codec, DynamicOps<FROM> ops, FROM from)
	{
		return codec.parse(ops, from).result().get();
	}

	public static <FROM, TO> TO write(Codec<FROM> codec, DynamicOps<TO> ops, FROM from)
	{
		return codec.encodeStart(ops, from).result().get();
	}

	public static <TO> TO readTag(Codec<TO> codec, Tag from)
	{
		return read(codec, NbtOps.INSTANCE, from);
	}

	public static <FROM> Tag writeTag(Codec<FROM> codec, FROM from)
	{
		return write(codec, NbtOps.INSTANCE, from);
	}

	private SerializationUtils()
	{

	}

}
