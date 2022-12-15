package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.item.NetheriteOxygenCanItem;
import ad_astra_giselle_addon.common.item.OxygenCanItem;
import net.minecraft.world.item.Item;

public class AddonItems
{
	public static final ItemRegistryCollection ITEMS = new ItemRegistryCollection(AdAstraGiselleAddon.MOD_ID);
	public static final ObjectRegistryHolder<OxygenCanItem> OXYGEN_CAN = ITEMS.add("oxygen_can", () -> new OxygenCanItem(new Item.Properties().tab(AddonTabs.tab_main)));
	public static final ObjectRegistryHolder<NetheriteOxygenCanItem> NETHERITE_OXYGEN_CAN = ITEMS.add("netherite_oxygen_can", () -> new NetheriteOxygenCanItem(new Item.Properties().tab(AddonTabs.tab_main)));

	public static Item.Properties getMainItemProperties()
	{
		return new Item.Properties().tab(AddonTabs.tab_main);
	}

	private AddonItems()
	{

	}

}
