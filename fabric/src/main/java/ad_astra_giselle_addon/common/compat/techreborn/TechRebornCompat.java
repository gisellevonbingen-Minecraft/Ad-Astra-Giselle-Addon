package ad_astra_giselle_addon.common.compat.techreborn;

import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;

import ad_astra_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;

public class TechRebornCompat extends CompatibleMod
{
	public static final String MOD_ID = "techreborn";

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
