package ad_astra_giselle_addon.common.registry;

import java.util.function.Consumer;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;

public class CreativeModeTabRegistryCollection extends ObjectRegistryCollection<CreativeModeTab>
{
	public CreativeModeTabRegistryCollection(String modid)
	{
		super(modid, Registries.CREATIVE_MODE_TAB);
	}

	public ObjectRegistryHolder<CreativeModeTab> add(String name, Consumer<CreativeModeTab.Builder> consumer)
	{
		return this.add(name, () ->
		{
			Builder builder = CreativeModeTabHelper.builder();
			consumer.accept(builder);
			return builder.build();
		});
	}

}
