package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.item.CreativeOxygenCanItem;
import ad_astra_giselle_addon.common.item.LanderIconItem;
import ad_astra_giselle_addon.common.item.NetheriteOxygenCanItem;
import ad_astra_giselle_addon.common.item.OxygenCanItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class AddonItems
{
	public static final ItemRegistryCollection ITEMS = new ItemRegistryCollection(AdAstraGiselleAddon.MOD_ID);
	public static final ObjectRegistryHolder<OxygenCanItem> OXYGEN_CAN = ITEMS.add("oxygen_can", () -> new OxygenCanItem(getMainItemProperties()));
	public static final ObjectRegistryHolder<NetheriteOxygenCanItem> NETHERITE_OXYGEN_CAN = ITEMS.add("netherite_oxygen_can", () -> new NetheriteOxygenCanItem(getMainItemProperties()));
	public static final ObjectRegistryHolder<CreativeOxygenCanItem> CREATIVE_OXYGEN_CAN = ITEMS.add("creative_oxygen_can", () -> new CreativeOxygenCanItem(getMainItemProperties().rarity(Rarity.EPIC)));
	public static final ObjectRegistryHolder<LanderIconItem> LANDER_ICON = ITEMS.add("lander_icon", () -> new LanderIconItem(new Item.Properties()));

	public static Item.Properties getMainItemProperties()
	{
		return new Item.Properties();
	}

	private AddonItems()
	{

	}

}
