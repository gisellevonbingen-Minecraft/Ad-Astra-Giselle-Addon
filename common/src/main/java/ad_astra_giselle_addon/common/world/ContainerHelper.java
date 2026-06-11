package ad_astra_giselle_addon.common.world;

import ad_astra_giselle_addon.common.item.StorageSlotContext;
import earth.terrarium.common_storage_lib.item.impl.vanilla.WrappedVanillaContainer;
import net.minecraft.world.Container;

public class ContainerHelper
{
	public static StorageSlotContext getItem(Container container, int slot)
	{
		WrappedVanillaContainer outerContainer = new WrappedVanillaContainer(container);
		return StorageSlotContext.ofSlot(outerContainer, outerContainer.get(slot));
	}

}
