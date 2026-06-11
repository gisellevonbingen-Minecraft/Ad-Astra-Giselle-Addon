package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registry.AddonItems;
import me.desht.pneumaticcraft.api.upgrade.PNCUpgrade;
import me.desht.pneumaticcraft.common.item.UpgradeItem;

public class AddonPNCUpgrades
{
	public static final UpgradeDeferredRegister UPGRADES = new UpgradeDeferredRegister(AdAstraGiselleAddon.MOD_ID);

	public static final UpgradeRegistryHolder<PNCUpgrade, UpgradeItem> OXYGEN_PROOF = UPGRADES.add("space_breathing", AddonItems::getMainItemProperties);
	public static final UpgradeRegistryHolder<PNCUpgrade, UpgradeItem> HOT_TEMPERATURE_PROOF = UPGRADES.add("space_fire_proof", AddonItems::getMainItemProperties);
	public static final UpgradeRegistryHolder<PNCUpgrade, UpgradeItem> ACID_RAIN_PROOF = UPGRADES.add("acid_rain_proof", AddonItems::getMainItemProperties);
	public static final UpgradeRegistryHolder<PNCUpgrade, UpgradeItem> GRAVITY_PROOF = UPGRADES.add("gravity_normalizing", AddonItems::getMainItemProperties);

	private AddonPNCUpgrades()
	{

	}

}
