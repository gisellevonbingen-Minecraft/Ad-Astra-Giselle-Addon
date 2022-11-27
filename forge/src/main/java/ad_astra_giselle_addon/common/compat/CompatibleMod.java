package ad_astra_giselle_addon.common.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public abstract class CompatibleMod
{
	private boolean isLoaded;

	public abstract String getModID();

	public void tryLoad()
	{
		if (ModList.get().isLoaded(this.getModID()))
		{
			this.isLoaded = true;

			try
			{
				this.onLoad();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	}

	protected abstract void onLoad();

	public boolean isLoaded()
	{
		return this.isLoaded;
	}

	public ResourceLocation getLocation(String path)
	{
		return new ResourceLocation(this.getModID(), path);
	}
}
