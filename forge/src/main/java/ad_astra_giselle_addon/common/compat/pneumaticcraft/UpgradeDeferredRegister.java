package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import ad_astra_giselle_addon.common.delegate.DelegateRegistryHelper;
import ad_astra_giselle_addon.common.registries.DelegateObjectCollection;
import ad_astra_giselle_addon.common.registries.DelegateObjectHolder;
import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import me.desht.pneumaticcraft.common.core.ModUpgrades;
import me.desht.pneumaticcraft.common.item.UpgradeItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class UpgradeDeferredRegister
{
	public static final ResourceKey<? extends Registry<PNCUpgrade>> REGISTRY_KEY = ModUpgrades.UPGRADES_DEFERRED.getRegistryKey();

	private final String modid;
	private final Set<UpgradeRegistryObject<?, ?>> objects;
	private final Set<UpgradeRegistryObject<?, ?>> readonlyObjects;

	protected final DelegateObjectCollection<PNCUpgrade> primaryRegister;
	protected final DelegateObjectCollection<Item> secondaryRegister;

	public UpgradeDeferredRegister(String modid)
	{
		this.modid = modid;
		this.objects = new HashSet<>();
		this.readonlyObjects = Collections.unmodifiableSet(this.objects);

		this.primaryRegister = new DelegateObjectCollection<>(modid, REGISTRY_KEY);
		this.secondaryRegister = new DelegateObjectCollection<>(modid, Registry.ITEM_REGISTRY);
	}

	public UpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> register(String name, Supplier<Item.Properties> propertiesSup)
	{
		return this.register(name, propertiesSup, 1);
	}

	public UpgradeRegistryObject<AddonPNCUpgrade, UpgradeItem> register(String name, Supplier<Item.Properties> propertiesSup, int maxTier, String... depModIds)
	{
		return this.register(name, () -> new AddonPNCUpgrade(maxTier, depModIds), u -> new UpgradeItem(u, maxTier, propertiesSup.get()));
	}

	@SuppressWarnings("unchecked")
	public <U extends AddonPNCUpgrade, I extends UpgradeItem> UpgradeRegistryObject<U, I> register(String name, Supplier<? extends U> upgradeSup, Function<DelegateObjectHolder<PNCUpgrade>, ? extends I> itemFunc)
	{
		U upgrade = upgradeSup.get();
		int maxTier = upgrade.getMaxTier();
		DelegateObjectHolder<? extends PNCUpgrade> upgradeRegistry = this.primaryRegister.register(name, () -> upgrade);

		List<DelegateObjectHolder<I>> items = new ArrayList<>();

		for (int i = 0; i < upgrade.getMaxTier(); i++)
		{
			items.add(this.secondaryRegister.register(AddonPNCUpgrade.getItemName(name, maxTier, i + 1), () -> itemFunc.apply((DelegateObjectHolder<PNCUpgrade>) upgradeRegistry)));
		}

		UpgradeRegistryObject<U, I> registryObject = new UpgradeRegistryObject<>((DelegateObjectHolder<U>) upgradeRegistry, items);
		this.objects.add(registryObject);
		return registryObject;
	}

	public void register(DelegateRegistryHelper factory)
	{
		this.primaryRegister.register(factory);
		this.secondaryRegister.register(factory);
	}

	public String getModid()
	{
		return this.modid;
	}

	public Collection<UpgradeRegistryObject<?, ?>> getObjects()
	{
		return this.readonlyObjects;
	}

}
