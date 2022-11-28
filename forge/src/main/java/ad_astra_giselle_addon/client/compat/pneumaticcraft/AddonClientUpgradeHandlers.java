package ad_astra_giselle_addon.client.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import ad_astra_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.handlers.AddonSimpleToggleableHandler;
import ad_astra_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.handlers.SpaceBreathingClientHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonCommonUpgradeHandlers;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.SpaceBreathingCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.SpaceFireProofCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.VenusAcidProofCommonHandler;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IArmorUpgradeClientHandler;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IArmorUpgradeClientHandler.SimpleToggleableHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.client.pneumatic_armor.ClientArmorRegistry;

public class AddonClientUpgradeHandlers
{
	private static final List<IArmorUpgradeClientHandler<? extends IArmorUpgradeHandler<?>>> HANDLERS = new ArrayList<>();
	private static final List<IArmorUpgradeClientHandler<? extends IArmorUpgradeHandler<?>>> READONLY_LIST = Collections.unmodifiableList(HANDLERS);

	public static final SpaceBreathingClientHandler<SpaceBreathingCommonHandler> SPACE_BREATHING = register(AddonCommonUpgradeHandlers.SPACE_BREATHING, SpaceBreathingClientHandler::new);
	public static final SimpleToggleableHandler<SpaceFireProofCommonHandler> SPACE_FIRE_PROOF = register(AddonCommonUpgradeHandlers.SPACE_FIRE_PROOF, AddonSimpleToggleableHandler::new);
	public static final SimpleToggleableHandler<VenusAcidProofCommonHandler> VENUS_ACID_PROOF = register(AddonCommonUpgradeHandlers.VENUS_ACID_PROOF, AddonSimpleToggleableHandler::new);

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
