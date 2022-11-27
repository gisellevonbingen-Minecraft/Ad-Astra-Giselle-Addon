package ad_astra_giselle_addon.common.config;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class MachinesConfig
{
	public final ConfigValue<Long> fuelLoader_capacity;
	public final ConfigValue<Long> fuelLoader_transfer;
	public final ConfigValue<Integer> fuelLoader_range;

	public MachinesConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("fuel_loader");

		this.fuelLoader_capacity = builder.define("fluidCapacity", FluidHooks.buckets(8));
		this.fuelLoader_transfer = builder.define("fluidTransfer", FluidHooks.toMillibuckets(256));
		builder.comment("blocks from FuelLoader to each direction");
		this.fuelLoader_range = builder.define("workingRange", 2);

		builder.pop();
	}

}
