package ad_astra_giselle_addon.common.compat;

import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;

import ad_astra_giselle_addon.common.util.ModHooks;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;

public abstract class CompatibleMod
{
	private boolean isLoaded;

	public abstract String getModId();

	public void tryLoad()
	{
		if (ModHooks.isLoaded(this.getModId()))
		{
			this.isLoaded = true;
			this.onLoad();
		}

	}

	protected abstract void onLoad();

	public boolean isLoaded()
	{
		return this.isLoaded;
	}

	public ResourceLocation getId(String path)
	{
		return ResourceLocation.fromNamespaceAndPath(this.getModId(), path);
	}

	public void collectEquipCommands(List<ArgumentBuilder<CommandSourceStack, ?>> list)
	{

	}

}
