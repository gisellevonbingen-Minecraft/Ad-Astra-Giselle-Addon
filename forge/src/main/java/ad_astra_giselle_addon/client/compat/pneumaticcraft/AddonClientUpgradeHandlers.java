package ad_astra_giselle_addon.client.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import ad_astra_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.handlers.AddonSimpleToggleableHandler;
import ad_astra_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.handlers.OxygenProofClientHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonCommonUpgradeHandlers;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.GravityProofCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.OxygenProofCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.HotTemperatureProofCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.AcidRainProofCommonHandler;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IArmorUpgradeClientHandler;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IArmorUpgradeClientHandler.SimpleToggleableHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.client.pneumatic_armor.ClientArmorRegistry;

public class AddonClientUpgradeHandlers
{
	private static final List<IArmorUpgradeClientHandler<? extends IArmorUpgradeHandler<?>>> HANDLERS = new ArrayList<>();
	private static final List<IArmorUpgradeClientHandler<? extends IArmorUpgradeHandler<?>>> READONLY_LIST = Collections.unmodifiableList(HANDLERS);

	public static final OxygenProofClientHandler<OxygenProofCommonHandler> OXYGEN_PROOF = register(AddonCommonUpgradeHandlers.OXYGEN_PROOF, OxygenProofClientHandler::new);
	public static final SimpleToggleableHandler<HotTemperatureProofCommonHandler> HOT_TEMPERATURE_PROOF = register(AddonCommonUpgradeHandlers.HOT_TEMPERATURE_PROOF, AddonSimpleToggleableHandler::new);
	public static final SimpleToggleableHandler<AcidRainProofCommonHandler> ACID_RAIN_PROOF = register(AddonCommonUpgradeHandlers.ACID_RAIN_PROOF, AddonSimpleToggleableHandler::new);
	public static final SimpleToggleableHandler<GravityProofCommonHandler> GRAVITY_PROOF = register(AddonCommonUpgradeHandlers.GRAVITY_PROOF, AddonSimpleToggleableHandler::new);

	private static <C extends IArmorUpgradeHandler<?>, T extends IArmorUpgradeClientHandler<? extends C>> T register(C commonHandler, Function<C, T> func)
	{
		T handler = func.apply(commonHandler);
		HANDLERS.add(handler);
		return handler;
	}

	@SuppressWarnings("unchecked")
	public static void register()
	{
		ClientArmorRegistry registry = ClientArmorRegistry.getInstance();

		for (IArmorUpgradeClientHandler<? extends IArmorUpgradeHandler<?>> handler : HANDLERS)
		{
			IArmorUpgradeHandler<?> commonHandler = handler.getCommonHandler();
			registry.registerUpgradeHandler(commonHandler, (IArmorUpgradeClientHandler<IArmorUpgradeHandler<?>>) handler);
		}

	}

	public static List<IArmorUpgradeClientHandler<?>> getHandlers()
	{
		return READONLY_LIST;
	}

}
