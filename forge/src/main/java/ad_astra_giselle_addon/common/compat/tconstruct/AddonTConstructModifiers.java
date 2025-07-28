package ad_astra_giselle_addon.common.compat.tconstruct;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class AddonTConstructModifiers
{
	public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(AdAstraGiselleAddon.MOD_ID);

	public static final StaticModifier<Modifier> OXYGEN_PROOF = MODIFIERS.register("space_breathing", NoLevelsModifier::new);
	public static final StaticModifier<Modifier> HOT_TEMPERATURE_PROOF = MODIFIERS.register("space_fire_proof", NoLevelsModifier::new);
	public static final StaticModifier<Modifier> ACID_RAIN_PROOF = MODIFIERS.register("acid_rain_proof", NoLevelsModifier::new);
	public static final StaticModifier<Modifier> GRAVITY_PROOF = MODIFIERS.register("gravity_normalizing", NoLevelsModifier::new);

	private AddonTConstructModifiers()
	{

	}

}
