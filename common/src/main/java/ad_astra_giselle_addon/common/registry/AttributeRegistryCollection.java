package ad_astra_giselle_addon.common.registry;

import java.util.function.Function;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AttributeRegistryCollection extends ObjectRegistryCollection<Attribute>
{
	public AttributeRegistryCollection(String modid)
	{
		super(modid, Registries.ATTRIBUTE);
	}

	public <I extends Attribute> ObjectRegistryHolder<I> add(String name, Function<String, ? extends I> initializer)
	{
		return super.add(name, () -> initializer.apply(name));
	}

}
