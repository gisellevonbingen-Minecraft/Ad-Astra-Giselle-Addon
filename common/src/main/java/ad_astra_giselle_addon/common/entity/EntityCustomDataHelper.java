package ad_astra_giselle_addon.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

public class EntityCustomDataHelper
{
	public static CompoundTag getCustomData(Entity entity)
	{
		if (entity instanceof ICustomDataHolder holder)
		{
			return holder.ad_astra_giselle_addon$getCustomData();
		}
		else
		{
			return new CompoundTag();
		}

	}

	private EntityCustomDataHelper()
	{

	}

}
