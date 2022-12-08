package ad_astra_giselle_addon.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class DelegateItemCollection extends DelegateObjectCollection<Item>
{
	public DelegateItemCollection(String modid)
	{
		super(modid, Registry.ITEM_REGISTRY);
	}

}
