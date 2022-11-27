package ad_astra_giselle_addon.common.config;

import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ItemsConfig
{
	public final ConfigValue<Long> oxygenCan_OxygenCapacity;
	public final ConfigValue<Long> oxygenCan_OxygenTransfer;

	public ItemsConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("oxygen_can");
		this.oxygenCan_OxygenCapacity = builder.define("oxygenCapacity", FluidHooks.buckets(2));
		this.oxygenCan_OxygenTransfer = builder.define("oxygenTransfer", FluidHooks.toMillibuckets(256));
		builder.pop();
	}

}
