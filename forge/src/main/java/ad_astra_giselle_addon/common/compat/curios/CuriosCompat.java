package ad_astra_giselle_addon.common.compat.curios;

import ad_astra_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CuriosCompat extends CompatibleMod
{
	public static final String MOD_ID = "curios";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}

	@Override
	public String getModId()
	{
		return MOD_ID;
	}

	@Override
	protected void onLoad()
	{
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerSlots);
	}

	public void registerSlots(InterModEnqueueEvent event)
	{
		InterModComms.sendTo(MOD_ID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CURIO.getMessageBuilder().build());
	}

}
