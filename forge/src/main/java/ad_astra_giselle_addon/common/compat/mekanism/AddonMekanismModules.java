package ad_astra_giselle_addon.common.compat.mekanism;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleGravityNormalizingUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleSpaceBreathingUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleSpaceFireProofUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleVenusAcidProofUnit;
import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;
import net.minecraft.world.item.Rarity;

public class AddonMekanismModules
{
	public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(AdAstraGiselleAddon.MOD_ID);

	public static final ModuleRegistryObject<ModuleSpaceBreathingUnit> SPACE_BREATHING_UNIT = MODULES.register("space_breathing_unit", ModuleSpaceBreathingUnit::new, () -> AddonMekanismItems.SPACE_BREATHING_UNIT.get(), m -> m.rarity(Rarity.UNCOMMON).maxStackSize(4).rendersHUD());
	public static final ModuleRegistryObject<ModuleGravityNormalizingUnit> GRAVITY_NORMALIZING_UNIT = MODULES.register("gravity_normalizing_unit", ModuleGravityNormalizingUnit::new, () -> AddonMekanismItems.GRAVITY_NORMALIZING_UNIT.get(), m -> m.rarity(Rarity.UNCOMMON));
	public static final ModuleRegistryObject<ModuleSpaceFireProofUnit> SPACE_FIRE_PROOF_UNIT = MODULES.register("space_fire_proof_unit", ModuleSpaceFireProofUnit::new, () -> AddonMekanismItems.SPACE_FIRE_PROOF_UNIT.get(), m -> m.rarity(Rarity.RARE));
	public static final ModuleRegistryObject<ModuleVenusAcidProofUnit> VENUS_ACID_PROOF_UNIT = MODULES.register("venus_acid_proof_unit", ModuleVenusAcidProofUnit::new, () -> AddonMekanismItems.VENUS_ACID_PROOF_UNIT.get(), m -> m.rarity(Rarity.RARE));

	private AddonMekanismModules()
	{

	}

}
