package ad_astra_giselle_addon.common.compat.redstonearsenal;

import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;

import ad_astra_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;

public class RedstoneArsenalCompat extends CompatibleMod
{
	public static final String MODID = "redstone_arsenal";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModId()
	{
		return MODID;
	}

	@Override
	protected void onLoad()
	{

	}

	@Override
	public void collectEquipCommands(List<ArgumentBuilder<CommandSourceStack, ?>> list)
	{
		super.collectEquipCommands(list);
		Commands.literal("flux_armor").executes(RSACommand::flux_armor);
	}

}
