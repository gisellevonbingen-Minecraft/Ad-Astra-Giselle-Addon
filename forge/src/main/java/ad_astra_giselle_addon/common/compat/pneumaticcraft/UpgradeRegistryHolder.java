package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.List;
import java.util.function.Supplier;

import ad_astra_giselle_addon.common.registry.ObjectRegistryHolder;
import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import me.desht.pneumaticcraft.common.item.UpgradeItem;
import net.minecraft.resources.ResourceLocation;

public class UpgradeRegistryHolder<U extends PNCUpgrade, I extends UpgradeItem> implements Supplier<U>
{
	private final ObjectRegistryHolder<? extends U> upgrade;
	private final List<? extends ObjectRegistryHolder<? extends I>> items;

	public UpgradeRegistryHolder(ObjectRegistryHolder<? extends U> upgrade, List<? extends ObjectRegistryHolder<? extends I>> items)
	{
		this.upgrade = upgrade;
		this.items = items;
	}

	public ResourceLocation getId()
	{
		return this.upgrade.getId();
	}

	@Override
	public U get()
	{
		return this.upgrade.get();
	}

	public ResourceLocation getItemId(int tier)
	{
		return this.items.get(tier - 1).getId();
	}

	public I getItem(int tier)
	{
		return this.items.get(tier - 1).get();
	}

	public List<ResourceLocation> getItemIds()
	{
		return this.items.stream().map(ObjectRegistryHolder::getId).toList();
	}

	public List<? extends I> getItems()
	{
		return this.items.stream().map(ObjectRegistryHolder::get).toList();
	}

}
