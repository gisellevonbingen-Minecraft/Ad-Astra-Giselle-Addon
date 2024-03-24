package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import ad_astra_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.resources.ResourceLocation;

public class PneumaticCraftCompat extends CompatibleMod
{
	public static final String MOD_ID = "pneumaticcraft";

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
