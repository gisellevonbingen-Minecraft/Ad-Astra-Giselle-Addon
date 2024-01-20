package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.GravityProofCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.OxygenProofCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.HotTemperatureProofCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.AcidRainProofCommonHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.common.pneumatic_armor.ArmorUpgradeRegistry;
import net.minecraft.resources.ResourceLocation;

public class AddonCommonUpgradeHandlers
{
	private static final List<IArmorUpgradeHandler<?>> HANDLERS = new ArrayList<>();
	private static final List<IArmorUpgradeHandler<?>> READONLY_LIST = Collections.unmodifiableList(HANDLERS);

	public static final OxygenProofCommonHandler OXYGEN_PROOF = register("space_breathing", OxygenProofCommonHandler::new);
	public static final HotTemperatureProofCommonHandler HOT_TEMPERATURE_PROOF = register("space_fire_proof", HotTemperatureProofCommonHandler::new);
	public static final AcidRainProofCommonHandler ACID_RAIN_PROOF = register("acid_rain_proof", AcidRainProofCommonHandler::new);
	public static final GravityProofCommonHandler GRAVITY_PROOF = register("gravity_normalizing", GravityProofCommonHandler::new);

	private static <T extends IArmorUpgradeHandler<?>> T register(String name, Function<ResourceLocation, T> func)
	{
		ResourceLocation id = AdAstraGiselleAddon.rl(name);
		T handler = func.apply(id);
		HANDLERS.add(handler);
		return handler;
	}

	public static void register()
	{
		ArmorUpgradeRegistry registry = ArmorUpgradeRegistry.getInstance();

		for (IArmorUpgradeHandler<?> handler : HANDLERS)
		{
			registry.registerUpgradeHandler(handler);
		}

	}

	public static List<IArmorUpgradeHandler<?>> getHandlers()
	{
		return READONLY_LIST;
	}

}
