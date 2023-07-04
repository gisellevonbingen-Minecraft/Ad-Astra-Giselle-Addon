package ad_astra_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.handlers;

import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IArmorUpgradeClientHandler.SimpleToggleableHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;

public class AddonSimpleToggleableHandler<T extends IArmorUpgradeHandler<?>> extends SimpleToggleableHandler<T>
{
	public AddonSimpleToggleableHandler(T commonHandler)
	{
		super(commonHandler);
	}

}
