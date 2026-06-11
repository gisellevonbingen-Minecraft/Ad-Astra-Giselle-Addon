package ad_astra_giselle_addon.common.registry;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DataComponentTypeRegistryHolder<T> extends ObjectRegistryHolder<DataComponentType<T>>
{
	public DataComponentTypeRegistryHolder(ResourceLocation id, Supplier<DataComponentType<T>> initializer, ResourceKey<? extends Registry<?>> key)
	{
		super(id, initializer, key);
	}

}
