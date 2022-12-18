package ad_astra_giselle_addon.common.item;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;

public interface SidedItemContainerBlock
{
	int getSideSlotLimit(int slot, @Nullable Direction directon);

	WorldlyContainer getContainer();
}
