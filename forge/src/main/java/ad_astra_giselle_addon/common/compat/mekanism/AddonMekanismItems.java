package ad_astra_giselle_addon.common.compat.mekanism;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registries.AddonTabs;
import mekanism.common.content.gear.ModuleHelper;
import mekanism.common.item.ItemModule;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.registration.impl.ModuleRegistryObject;

public class AddonMekanismItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(AdAstraGiselleAddon.MOD_ID);

	public static final ItemRegistryObject<ItemModule> SPACE_BREATHING_UNIT = registerModule(ITEMS, AddonMekanismModules.SPACE_BREATHING_UNIT);
	public static final ItemRegistryObject<ItemModule> SPACE_FIRE_PROOF_UNIT = registerModule(ITEMS, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT);
	public static final ItemRegistryObject<ItemModule> ACID_RAIN_PROOF_UNIT = registerModule(ITEMS, AddonMekanismModules.ACID_RAIN_PROOF_UNIT);

	public static ItemRegistryObject<ItemModule> registerModule(ItemDeferredRegister register, ModuleRegistryObject<?> moduleDataSupplier)
	{
		return register.register("module_" + moduleDataSupplier.getInternalRegistryName(), () -> ModuleHelper.INSTANCE.createModuleItem(moduleDataSupplier, ItemDeferredRegister.getMekBaseProperties().tab(AddonTabs.tab_main)));
	}

	private AddonMekanismItems()
	{

	}

}
