package ad_astra_giselle_addon.common.compat.mekanism;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registry.AddonTabs;
import mekanism.common.content.gear.ModuleHelper;
import mekanism.common.item.ItemModule;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.registration.impl.ModuleRegistryObject;

public class AddonMekanismItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(AdAstraGiselleAddon.MOD_ID);

	public static final ItemRegistryObject<ItemModule> OXYGEN_PROOF_UNIT = registerModule(ITEMS, AddonMekanismModules.OXYGEN_PROOF_UNIT);
	public static final ItemRegistryObject<ItemModule> HOT_TEMPERATURE_PROOF_UNIT = registerModule(ITEMS, AddonMekanismModules.HOT_TEMPERATURE_PROOF_UNIT);
	public static final ItemRegistryObject<ItemModule> ACID_RAIN_PROOF_UNIT = registerModule(ITEMS, AddonMekanismModules.ACID_RAIN_PROOF_UNIT);
	public static final ItemRegistryObject<ItemModule> GRAVITY_PROOF_UNIT = registerModule(ITEMS, AddonMekanismModules.GRAVITY_PROOF_UNIT);

	public static ItemRegistryObject<ItemModule> registerModule(ItemDeferredRegister register, ModuleRegistryObject<?> moduleDataSupplier)
	{
		return register.register("module_" + moduleDataSupplier.getInternalRegistryName(), () -> ModuleHelper.INSTANCE.createModuleItem(moduleDataSupplier, ItemDeferredRegister.getMekBaseProperties().tab(AddonTabs.tab_main)));
	}

	private AddonMekanismItems()
	{

	}

}
