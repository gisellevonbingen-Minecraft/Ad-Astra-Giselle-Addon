package ad_astra_giselle_addon.common.compat;

import net.minecraft.resources.ResourceLocation;

public class ReiCompat extends CompatibleMod
{
	public static final String MOD_ID = "rei";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}

	@Override
	public String getModId()
	{
		return MOD_ID;
	}

	@Override
	protected void onLoad()
	{

	}

}
