package ad_astra_giselle_addon.common.compat.jei;

import ad_astra_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.resources.ResourceLocation;

public class JeiCompat extends CompatibleMod
{
	public static final String MOD_ID = "jei";

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
