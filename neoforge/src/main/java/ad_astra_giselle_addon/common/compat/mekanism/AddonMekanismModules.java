package ad_astra_giselle_addon.common.compat.mekanism;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleAcidRainProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleGravityProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleHotTemperatureProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleOxygenProofUnit;
import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import net.minecraft.world.item.Rarity;

public class AddonMekanismModules
{
	public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(AdAstraGiselleAddon.MOD_ID);

	public static final ModuleRegistryObject<ModuleOxygenProofUnit> OXYGEN_PROOF_UNIT = MODULES.register("space_breathing_unit", ModuleOxygenProofUnit::new, () -> AddonMekanismItems.OXYGEN_PROOF_UNIT.get(), m -> m.rarity(Rarity.UNCOMMON).maxStackSize(4).rendersHUD());
	public static final ModuleRegistryObject<ModuleHotTemperatureProofUnit> HOT_TEMPERATURE_PROOF_UNIT = MODULES.register("space_fire_proof_unit", ModuleHotTemperatureProofUnit::new, () -> AddonMekanismItems.HOT_TEMPERATURE_PROOF_UNIT.get(), m -> m.rarity(Rarity.RARE));
	public static final ModuleRegistryObject<ModuleAcidRainProofUnit> ACID_RAIN_PROOF_UNIT = MODULES.register("acid_rain_proof_unit", ModuleAcidRainProofUnit::new, () -> AddonMekanismItems.ACID_RAIN_PROOF_UNIT.get(), m -> m.rarity(Rarity.RARE));
	public static final ModuleRegistryObject<ModuleGravityProofUnit> GRAVITY_PROOF_UNIT = MODULES.register("gravity_normalizing_unit", ModuleGravityProofUnit::new, () -> AddonMekanismItems.GRAVITY_PROOF_UNIT.get(), m -> m.rarity(Rarity.RARE));

	private AddonMekanismModules()
	{

	}

}
