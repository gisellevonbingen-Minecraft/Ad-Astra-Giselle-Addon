package ad_astra_giselle_addon.common.world;

import ad_astra_giselle_addon.common.item.ItemStackConsumers;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import net.minecraft.world.Container;

public class ContainerHelper
{
	public static ItemStackReference getItem(Container container, int slot)
	{
		return new ItemStackReference(container.getItem(slot), ItemStackConsumers.index(slot, container::setItem));
	}

}
