package ad_astra_giselle_addon.common.compat.mekanism;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class MekanismConfig
{
	public final ConfigValue<Integer> moduleSpaceBreathing_oxygenDuration;
	public final ConfigValue<Integer> moduleSpaceBreathing_energyUsingProvide;
	public final ConfigValue<Integer> moduleSpaceBreathing_energyUsingProduce;

	public final ConfigValue<Integer> moduleSpaceFireProof_energyUsing;
	public final ConfigValue<Integer> moduleVenusAcidProof_energyUsing;

	public MekanismConfig(ForgeConfigSpec.Builder builder)
	{
		builder.push("module_space_breathing_unit");
		builder.comment("Duration of provided oxygen (oxygen provide interval)");
		this.moduleSpaceBreathing_oxygenDuration = builder.define("oxygenDuration", 4);
		builder.comment("Energy usage when provide Ad Astra oxygen to player in space");
		this.moduleSpaceBreathing_energyUsingProvide = builder.define("energyUsing", 10);
		builder.comment("Energy usage per mb when produce Mekanism oxygen to player in water, rain");
		this.moduleSpaceBreathing_energyUsingProduce = builder.define("energyUsingProduce", 200);
		builder.pop();

		builder.push("module_space_fire_proof_unit");
		builder.comment("Energy usage when prevent fire every tick in Ad Astra hot planets (e.g. Venus, Mercury)");
		this.moduleSpaceFireProof_energyUsing = builder.define("energyUsing", 10);
		builder.pop();

		builder.push("module_venus_acid_proof_unit");
		builder.comment("Energy usage when prevent acid rain damage every tick in Ad Astra venus");
		this.moduleVenusAcidProof_energyUsing = builder.define("energyUsing", 10);
		builder.pop();
	}

}
