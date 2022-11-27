package ad_astra_giselle_addon.common.compat.mekanism;

import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleSpaceBreathingUnit;
import ad_astra_giselle_addon.common.compat.mekanism.gear.ModuleVenusAcidProofUnit;
import ad_astra_giselle_addon.common.content.proof.LivingSpaceOxygenProofEvent;
import mekanism.common.content.gear.Module;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MekanismCommonEventListener
{
	@SubscribeEvent
	public static void onLivingSpaceOxygenProofEvent(LivingSpaceOxygenProofEvent e)
	{
		LivingEntity entity = e.getEntity();
		Module<ModuleSpaceBreathingUnit> module = AddonModuleHelper.findArmorEnabledModule(entity, AddonMekanismModules.SPACE_BREATHING_UNIT);

		if (module != null)
		{
			int provideOxygen = module.getCustomInstance().provideOxygen(module, entity);
			e.setProofDuration(provideOxygen);
		}

	}

	@SubscribeEvent
	public static void onLivingSetVenusRain(LivingSpaceOxygenProofEvent e)
	{
		boolean cancelled = AddonModuleHelper.tryCancel(e, AddonMekanismModules.VENUS_ACID_PROOF_UNIT, ModuleVenusAcidProofUnit::getEnergyUsing);

		if (cancelled)
		{
			e.setProofDuration(1);
		}

	}

	private MekanismCommonEventListener()
	{

	}

}
