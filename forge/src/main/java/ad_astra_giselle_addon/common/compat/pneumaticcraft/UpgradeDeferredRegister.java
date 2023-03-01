package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import ad_astra_giselle_addon.common.registry.ObjectRegistryCollection;
import ad_astra_giselle_addon.common.registry.ObjectRegistryHolder;
import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import me.desht.pneumaticcraft.common.core.ModUpgrades;
import me.desht.pneumaticcraft.common.item.UpgradeItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class UpgradeDeferredRegister
{
	public static final ResourceKey<? extends Registry<PNCUpgrade>> REGISTRY_KEY = ModUpgrades.UPGRADES_DEFERRED.getRegistryKey();

	private final String modid;
	private final Set<UpgradeRegistryHolder<?, ?>> objects;
	private final Set<UpgradeRegistryHolder<?, ?>> readonlyObjects;

	protected final ObjectRegistryCollection<PNCUpgrade> primaryRegister;
	protected final ObjectRegistryCollection<Item> secondaryRegister;

	public UpgradeDeferredRegister(String modid)
	{
		this.modid = modid;
		this.objects = new HashSet<>();
		this.readonlyObjects = Collections.unmodifiableSet(this.objects);

		this.primaryRegister = new ObjectRegistryCollection<>(modid, REGISTRY_KEY);
		this.secondaryRegister = new ObjectRegistryCollection<>(modid, Registries.ITEM);
	}

	public UpgradeRegistryHolder<AddonPNCUpgrade, UpgradeItem> add(String name, Supplier<Item.Properties> propertiesSup)
	{
		return this.add(name, propertiesSup, 1);
	}

	public UpgradeRegistryHolder<AddonPNCUpgrade, UpgradeItem> add(String name, Supplier<Item.Properties> propertiesSup, int maxTier, String... depModIds)
	{
		return this.add(name, () -> new AddonPNCUpgrade(maxTier, depModIds), u -> new UpgradeItem(u, maxTier, propertiesSup.get()));
	}

	@SuppressWarnings("unchecked")
	public <U extends AddonPNCUpgrade, I extends UpgradeItem> UpgradeRegistryHolder<U, I> add(String name, Supplier<? extends U> upgradeSup, Function<ObjectRegistryHolder<PNCUpgrade>, ? extends I> itemFunc)
	{
		U upgrade = upgradeSup.get();
		int maxTier = upgrade.getMaxTier();
		ObjectRegistryHolder<? extends PNCUpgrade> upgradeRegistry = this.primaryRegister.add(name, () -> upgrade);

		List<ObjectRegistryHolder<I>> items = new ArrayList<>();

		for (int i = 0; i < upgrade.getMaxTier(); i++)
		{
			items.add(this.secondaryRegister.add(AddonPNCUpgrade.getItemName(name, maxTier, i + 1), () -> itemFunc.apply((ObjectRegistryHolder<PNCUpgrade>) upgradeRegistry)));
		}

		UpgradeRegistryHolder<U, I> registryObject = new UpgradeRegistryHolder<>((ObjectRegistryHolder<U>) upgradeRegistry, items);
		this.objects.add(registryObject);
		return registryObject;
	}

	public void register()
	{
		this.primaryRegister.register();
		this.secondaryRegister.register();
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
