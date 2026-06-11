package ad_astra_giselle_addon.common.entity;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.entities.vehicles.Rocket.RocketProperties;
import earth.terrarium.adastra.common.entities.vehicles.Rover;
import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import earth.terrarium.adastra.common.tags.ModFluidTags;

public class VehicleHelper
{
	public static @Nullable VehicleFuelInformation getFuelInformation(Vehicle vehicle)
	{
		if (vehicle instanceof Rocket rocket)
		{
			RocketProperties properties = Rocket.ROCKET_TO_PROPERTIES.get(rocket.getType());

			if (properties == null)
			{
				return null;
			}

			return new VehicleFuelInformation(rocket.getFluids(null), properties.fuel());
		}
		else if (vehicle instanceof Rover rover)
		{
			return new VehicleFuelInformation(rover.getFluids(null), ModFluidTags.TIER_1_ROVER_FUEL);
		}
		else
		{
			return null;
		}

	}

	private VehicleHelper()
	{

	}

}
