package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import ad_astra_giselle_addon.client.compat.pneumaticcraft.AddonPneumaticCraftCompatClient;
import ad_astra_giselle_addon.common.compat.CompatibleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class PneumaticCraftCompat extends CompatibleMod
{
	public static final String MODID = "pneumaticcraft";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID()
	{
		return MODID;
	}

	@Override
	protected void onLoad()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonPNCUpgrades.UPGRADES.register(fml_bus);
		AddonCommonUpgradeHandlers.register();

		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(PneumaticCraftEventHandler.class);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AddonPneumaticCraftCompatClient::new);
	}

}
