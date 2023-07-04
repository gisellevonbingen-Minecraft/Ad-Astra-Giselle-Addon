package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import ad_astra_giselle_addon.common.registry.ObjectRegistryCollection;
import ad_astra_giselle_addon.common.registry.ObjectRegistryHolder;
import me.desht.pneumaticcraft.api.PneumaticRegistry;
import me.desht.pneumaticcraft.api.upgrade.PNCUpgrade;
import me.desht.pneumaticcraft.common.item.UpgradeItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class UpgradeDeferredRegister
{
	public static ResourceLocation getItemName(PNCUpgrade upgrade, int tier)
	{
		ResourceLocation id = upgrade.getId();
		return new ResourceLocation(id.getNamespace(), getItemName(id.getPath(), upgrade.getMaxTier(), tier));
	}

	public static String getItemName(String name, int maxTier, int tier)
	{
		String prefix = "pneumatic_" + name + "_upgrade";

		if (maxTier == 1)
		{
			return prefix;
		}
		else
		{
			return prefix + "_" + tier;
		}

	}

	private final String modid;
	private final Set<UpgradeRegistryHolder<?, ?>> objects;
	private final Set<UpgradeRegistryHolder<?, ?>> readonlyObjects;

	protected final ObjectRegistryCollection<Item> itemRegister;

	public UpgradeDeferredRegister(String modid)
	{
		this.modid = modid;
		this.objects = new HashSet<>();
		this.readonlyObjects = Collections.unmodifiableSet(this.objects);

		this.itemRegister = new ObjectRegistryCollection<>(modid, Registries.ITEM);
	}

	public Optional<UpgradeRegistryHolder<?, ?>> find(PNCUpgrade upgrade)
	{
		for (UpgradeRegistryHolder<?, ?> holder : this.getObjects())
		{
			if (holder.get() == upgrade)
			{
				return Optional.of(holder);
			}

		}

		return Optional.empty();
	}

	public UpgradeRegistryHolder<PNCUpgrade, UpgradeItem> add(String name, Supplier<Item.Properties> propertiesSup)
	{
		return this.add(name, propertiesSup, 1);
	}

	public UpgradeRegistryHolder<PNCUpgrade, UpgradeItem> add(String name, Supplier<Item.Properties> propertiesSup, int maxTier, String... depModIds)
	{
		return this.add(name, () -> PneumaticRegistry.getInstance().getUpgradeRegistry().registerUpgrade(new ResourceLocation(this.getModid(), name)), u -> new UpgradeItem(u, maxTier, propertiesSup.get()));
	}

	public <U extends PNCUpgrade, I extends UpgradeItem> UpgradeRegistryHolder<U, I> add(String name, Supplier<? extends U> upgradeSup, Function<PNCUpgrade, ? extends I> itemFunc)
	{
		U upgrade = upgradeSup.get();
		int maxTier = upgrade.getMaxTier();

		List<ObjectRegistryHolder<I>> items = new ArrayList<>();

		for (int i = 0; i < upgrade.getMaxTier(); i++)
		{
			items.add(this.itemRegister.add(getItemName(name, maxTier, i + 1), () -> itemFunc.apply(upgrade)));
		}

		UpgradeRegistryHolder<U, I> registryObject = new UpgradeRegistryHolder<>(upgrade, items);
		this.objects.add(registryObject);
		return registryObject;
	}

	public void register()
	{
		this.itemRegister.register();
	}

	public String getModid()
	{
		return this.modid;
	}

	public Collection<UpgradeRegistryHolder<?, ?>> getObjects()
	{
		return this.readonlyObjects;
	}

}
