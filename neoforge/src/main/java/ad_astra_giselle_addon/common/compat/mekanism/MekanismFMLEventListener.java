package ad_astra_giselle_addon.common.compat.mekanism;

import mekanism.api.MekanismIMC;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;

public class MekanismFMLEventListener
{
	@SubscribeEvent
	public static void onInterModeEnqueue(InterModEnqueueEvent event)
	{
		String modid = MekanismCompat.MOD_ID;
		InterModComms.sendTo(modid, MekanismIMC.ADD_MEKA_SUIT_HELMET_MODULES, AddonMekanismModules.OXYGEN_PROOF_UNIT);
		InterModComms.sendTo(modid, MekanismIMC.ADD_MEKA_SUIT_BODYARMOR_MODULES, AddonMekanismModules.HOT_TEMPERATURE_PROOF_UNIT);
		InterModComms.sendTo(modid, MekanismIMC.ADD_MEKA_SUIT_BODYARMOR_MODULES, AddonMekanismModules.ACID_RAIN_PROOF_UNIT);
		InterModComms.sendTo(modid, MekanismIMC.ADD_MEKA_SUIT_BOOTS_MODULES, AddonMekanismModules.GRAVITY_PROOF_UNIT);
	}

	private MekanismFMLEventListener()
	{

	}

}
