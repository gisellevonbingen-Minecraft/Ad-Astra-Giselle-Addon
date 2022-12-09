package ad_astra_giselle_addon.common.registry;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class DelegateAttributeCollection extends DelegateObjectCollection<Attribute>
{
	public DelegateAttributeCollection(String modid)
	{
		super(modid, Registry.ATTRIBUTE_REGISTRY);
	}

	public <I extends Attribute> DelegateObjectHolder<I> add(String name, Function<String, ? extends I> initializer)
	{
		return super.add(name, () -> initializer.apply(name));
	}

}
