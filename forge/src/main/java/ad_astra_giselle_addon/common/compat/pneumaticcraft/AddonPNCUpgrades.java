package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registry.AddonItems;
import me.desht.pneumaticcraft.common.item.UpgradeItem;

public class AddonPNCUpgrades
{
	public static final UpgradeDeferredRegister UPGRADES = new UpgradeDeferredRegister(AdAstraGiselleAddon.MOD_ID);

	public static final UpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> SPACE_BREATHING = UPGRADES.add("space_breathing", AddonItems::getMainItemProperties);
	public static final UpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> SPACE_FIRE_PROOF = UPGRADES.add("space_fire_proof", AddonItems::getMainItemProperties);
	public static final UpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> ACID_RAIN_PROOF = UPGRADES.add("acid_rain_proof", AddonItems::getMainItemProperties);

	private AddonPNCUpgrades()
	{

	}

}
