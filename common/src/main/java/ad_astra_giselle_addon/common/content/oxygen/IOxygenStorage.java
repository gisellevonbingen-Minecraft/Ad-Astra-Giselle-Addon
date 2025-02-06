package ad_astra_giselle_addon.common.content.oxygen;

import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import net.minecraft.world.entity.LivingEntity;

public interface IOxygenStorage
{
	long extractOxygen(@Nullable LivingEntity entity, long amount, boolean simulate);

	long getOxygenAmount();

	long getOxygenCapacity();

	default boolean testTemperature(int temperature)
	{
		Range<Integer> temperatureThreshold = this.getTemperatureThreshold();
		return temperatureThreshold == null || temperatureThreshold.contains(temperature);
	}

	Range<Integer> getTemperatureThreshold();

	default double getOxygenStoredRatio()
	{
		long amount = this.getOxygenAmount();
		long capacity = this.getOxygenCapacity();
		return FluidHooks2.getStoredRatio(amount, capacity);
	}

}
