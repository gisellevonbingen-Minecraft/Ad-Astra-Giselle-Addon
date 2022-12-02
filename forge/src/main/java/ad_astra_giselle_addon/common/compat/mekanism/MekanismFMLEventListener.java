package ad_astra_giselle_addon.common.compat.mekanism;

import mekanism.api.MekanismIMC;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class MekanismFMLEventListener
{
	@SubscribeEvent
	public static void onInterModeEnqueue(InterModEnqueueEvent event)
	{
		String modid = MekanismCompat.MOD_ID;
		InterModComms.sendTo(modid, MekanismIMC.ADD_MEKA_SUIT_HELMET_MODULES, AddonMekanismModules.SPACE_BREATHING_UNIT);
		InterModComms.sendTo(modid, MekanismIMC.ADD_MEKA_SUIT_BODYARMOR_MODULES, AddonMekanismModules.VENUS_ACID_PROOF_UNIT);
	}

	private MekanismFMLEventListener()
	{

	}

}
