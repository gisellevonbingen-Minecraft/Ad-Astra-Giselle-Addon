package ad_astra_giselle_addon.common.item.crafting;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import ad_astra_giselle_addon.common.config.AddonConfigs;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class TagPreference
{
	private static final ResourceLocation DEFAULT_ID = new ResourceLocation("zzzzz:zzzzz");

	private static final Map<ResourceLocation, Optional<?>> PREFERENCE_CACHE = new HashMap<>();

	private static final Map<ResourceKey<?>, RegistryComparator<?>> COMPARATOR_CACHE = new HashMap<>();

	public static void register(IEventBus bus)
	{
		bus.addListener(TagPreference::onTagsUpdated);
	}

	private static void onTagsUpdated(TagsUpdatedEvent event)
	{
		PREFERENCE_CACHE.clear();
	}

	@SuppressWarnings("unchecked")
	private static <T> Comparator<T> getComparator(Registry<T> registry)
	{
		return (Comparator<T>) COMPARATOR_CACHE.computeIfAbsent(registry.key(), k -> new RegistryComparator<>(registry));
	}

	@SuppressWarnings("unchecked")
	private static <T> Optional<T> getUncachedPreference(TagKey<T> tag)
	{
		Registry<T> registry = (Registry<T>) Registry.REGISTRY.get(tag.registry().location());

		if (registry == null)
		{
			return Optional.empty();
		}
		else
		{
			return StreamSupport.stream(registry.getTagOrEmpty(tag).spliterator(), false).filter(Holder::isBound).map(Holder::value).min(getComparator(registry));
		}

	}

	@SuppressWarnings("unchecked")
	public static <T> Optional<T> getPreference(TagKey<T> tag)
	{
		return (Optional<T>) PREFERENCE_CACHE.computeIfAbsent(tag.location(), name -> getUncachedPreference(tag));
	}

	private record RegistryComparator<T> (Registry<T> registry) implements Comparator<T>
	{
		public int defaultIf(int check, int undesired, int fallback)
		{
			return check == undesired ? fallback : check;
		}

		@Override
		public int compare(T a, T b)
		{
			Registry<T> registry = this.registry;
			ResourceLocation idA = Objects.requireNonNullElse(registry.getKey(a), DEFAULT_ID);
			ResourceLocation idB = Objects.requireNonNullElse(registry.getKey(b), DEFAULT_ID);
			List<? extends String> entries = AddonConfigs.Common.recipes.tagPreferences.get();
			int size = entries.size();
			int indexA = this.defaultIf(entries.indexOf(idA.getNamespace()), -1, size);
			int indexB = this.defaultIf(entries.indexOf(idB.getNamespace()), -1, size);

			if (indexA != indexB)
			{
				return Integer.compare(indexA, indexB);
			}
			else
			{
				return idA.compareNamespaced(idB);
			}

		}

	}

}
