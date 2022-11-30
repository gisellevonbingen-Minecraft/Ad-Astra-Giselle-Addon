package ad_astra_giselle_addon.common.delegate;

import java.util.List;

import ad_astra_giselle_addon.common.item.ItemStackReference;
import net.minecraft.world.entity.LivingEntity;

public interface DelegateLivingHelper
{
	List<ItemStackReference> getExtraInventoryStacks(LivingEntity living);
}
