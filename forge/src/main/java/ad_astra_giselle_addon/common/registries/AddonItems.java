package ad_astra_giselle_addon.common.registries;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.item.OxygenCanItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class AddonItems
{
	public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(AdAstraGiselleAddon.MOD_ID);
	public static final RegistryObject<OxygenCanItem> OXYGEN_CAN = ITEMS.register("oxygen_can", () -> new OxygenCanItem(new Item.Properties().tab(AddonTabs.tab_main)));

	public static Item.Properties getMainItemProperties()
	{
		return new Item.Properties().tab(AddonTabs.tab_main);
	}

	private AddonItems()
	{

	}

}
