package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.List;
import java.util.function.Supplier;

import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import me.desht.pneumaticcraft.common.item.UpgradeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class UpgradeRegistryObject<U extends PNCUpgrade, I extends UpgradeItem> implements Supplier<U>
{
	private final RegistryObject<? extends U> upgrade;
	private final List<? extends RegistryObject<? extends I>> items;

	public UpgradeRegistryObject(RegistryObject<? extends U> upgrade, List<? extends RegistryObject<? extends I>> items)
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
		return this.items.stream().map(RegistryObject::getId).toList();
	}

	public List<? extends I> getItems()
	{
		return this.items.stream().map(RegistryObject::get).toList();
	}

}
