package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.GravityNormalizingCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.SpaceBreathingCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.SpaceFireProofCommonHandler;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.VenusAcidProofCommonHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.common.pneumatic_armor.ArmorUpgradeRegistry;
import net.minecraft.resources.ResourceLocation;

public class AddonCommonUpgradeHandlers
{
	private static final List<IArmorUpgradeHandler<?>> HANDLERS = new ArrayList<>();
	private static final List<IArmorUpgradeHandler<?>> READONLY_LIST = Collections.unmodifiableList(HANDLERS);

	public static final SpaceBreathingCommonHandler SPACE_BREATHING = register("space_breathing", SpaceBreathingCommonHandler::new);
	public static final GravityNormalizingCommonHandler GRAVITY_NORMALIZING = register("gravity_normalizing", GravityNormalizingCommonHandler::new);
	public static final SpaceFireProofCommonHandler SPACE_FIRE_PROOF = register("space_fire_proof", SpaceFireProofCommonHandler::new);
	public static final VenusAcidProofCommonHandler VENUS_ACID_PROOF = register("venus_acid_proof", VenusAcidProofCommonHandler::new);

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
