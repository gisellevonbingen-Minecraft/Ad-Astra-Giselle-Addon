package ad_astra_giselle_addon.common.compat.redstonearsenal;

import ad_astra_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.resources.ResourceLocation;

public class RedstoneArsenalCompat extends CompatibleMod
{
	public static final String MODID = "redstone_arsenal";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	protected void onLoad()
	{

	}

}
