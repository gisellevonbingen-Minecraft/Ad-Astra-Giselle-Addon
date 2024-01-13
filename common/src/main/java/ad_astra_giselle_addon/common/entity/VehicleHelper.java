package ad_astra_giselle_addon.common.entity;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.entities.vehicles.Rover;
import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;

public class VehicleHelper
{
	public static @Nullable FluidContainer getFluidContainer(Vehicle vehicle)
	{
		if (vehicle instanceof Rocket rocket)
		{
			return rocket.fluidContainer();
		}
		else if (vehicle instanceof Rover rover)
		{
			return rover.fluidContainer();
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
