package ad_astra_giselle_addon.common.registry;

import java.util.function.UnaryOperator;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class DataComponentTypeRegistryCollection extends ObjectRegistryCollection<DataComponentType<?>>
{
	public DataComponentTypeRegistryCollection(String modid)
	{
		super(modid, Registries.DATA_COMPONENT_TYPE);
	}

	public <T> DataComponentTypeRegistryHolder<T> add(String name, UnaryOperator<DataComponentType.Builder<T>> builder)
	{
		return this.add(name, () -> builder.apply(DataComponentType.<T> builder()).build(), DataComponentTypeRegistryHolder<T>::new);
	}

}
