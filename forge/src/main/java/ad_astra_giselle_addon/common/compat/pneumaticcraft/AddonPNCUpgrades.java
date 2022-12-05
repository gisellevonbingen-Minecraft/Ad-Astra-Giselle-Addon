package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registries.AddonItems;
import me.desht.pneumaticcraft.common.item.UpgradeItem;

public class AddonPNCUpgrades
{
	public static final UpgradeDeferredRegister UPGRADES = new UpgradeDeferredRegister(AdAstraGiselleAddon.MOD_ID);

	public static final UpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> SPACE_BREATHING = UPGRADES.register("space_breathing", AddonItems::getMainItemProperties);
	public static final UpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> SPACE_FIRE_PROOF = UPGRADES.register("space_fire_proof", AddonItems::getMainItemProperties);
	public static final UpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> ACID_RAIN_PROOF = UPGRADES.register("acid_rain_proof", AddonItems::getMainItemProperties);

	private AddonPNCUpgrades()
	{

	}

}
