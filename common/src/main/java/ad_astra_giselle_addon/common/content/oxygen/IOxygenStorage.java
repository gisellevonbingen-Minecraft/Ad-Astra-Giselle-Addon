package ad_astra_giselle_addon.common.content.oxygen;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.LivingEntity;

public interface IOxygenStorage
{
	long extractOxygen(@Nullable LivingEntity entity, long amount, boolean simulate);

	long getOxygenAmount();

	long getOxygenCapacity();

	boolean canUseOnCold();

	boolean canUseOnHot();

	public default boolean canUse(boolean isCold, boolean isHot)
	{
		if (isCold && !this.canUseOnCold())
		{
			return false;
		}
		else if (isHot && !this.canUseOnHot())
		{
			return false;
		}
		else
		{
			return true;
		}

	}

}
