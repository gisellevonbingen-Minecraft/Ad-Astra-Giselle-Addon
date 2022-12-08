package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.List;
import java.util.function.Supplier;

import ad_astra_giselle_addon.common.registry.DelegateObjectHolder;
import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import me.desht.pneumaticcraft.common.item.UpgradeItem;
import net.minecraft.resources.ResourceLocation;

public class UpgradeRegistryObject<U extends PNCUpgrade, I extends UpgradeItem> implements Supplier<U>
{
	private final DelegateObjectHolder<? extends U> upgrade;
	private final List<? extends DelegateObjectHolder<? extends I>> items;

	public UpgradeRegistryObject(DelegateObjectHolder<? extends U> upgrade, List<? extends DelegateObjectHolder<? extends I>> items)
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
		return this.items.stream().map(DelegateObjectHolder::getId).toList();
	}

	public List<? extends I> getItems()
	{
		return this.items.stream().map(DelegateObjectHolder::get).toList();
	}

}
