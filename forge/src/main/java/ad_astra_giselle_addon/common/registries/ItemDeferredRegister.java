package ad_astra_giselle_addon.common.registries;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemDeferredRegister extends DeferredRegisterWrapper<Item>
{
	public ItemDeferredRegister(String modid)
	{
		super(modid, ForgeRegistries.ITEMS);
	}

}
