package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AddonTags
{
	public static class Items
	{
		public static final TagKey<Item> OXYGEN_STORAGES = TagKey.create(Registries.ITEM, AdAstraGiselleAddon.rl("oxygen_storages"));
		public static final TagKey<Item> OXYGEN_STORAGES_COLD = TagKey.create(Registries.ITEM, AdAstraGiselleAddon.rl("oxygen_storages/cold"));
		public static final TagKey<Item> OXYGEN_STORAGES_HOT = TagKey.create(Registries.ITEM, AdAstraGiselleAddon.rl("oxygen_storages/hot"));
	}

	private AddonTags()
	{

	}

}
