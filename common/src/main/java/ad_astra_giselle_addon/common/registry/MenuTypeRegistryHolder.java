package ad_astra_giselle_addon.common.registry;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class MenuTypeRegistryHolder<MENU extends AbstractContainerMenu> extends ObjectRegistryHolder<MenuType<MENU>>
{
	public MenuTypeRegistryHolder(ResourceLocation id, Supplier<? extends MenuType<MENU>> initializer, ResourceKey<? extends Registry<?>> key)
	{
		super(id, initializer, key);
	}

}
