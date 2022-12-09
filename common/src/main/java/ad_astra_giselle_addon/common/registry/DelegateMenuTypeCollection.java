package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.util.TriFunction;
import earth.terrarium.botarium.api.registry.RegistryHelpers;
import earth.terrarium.botarium.api.registry.RegistryHelpers.MenuFactory;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DelegateMenuTypeCollection extends ObjectRegistryCollection<MenuType<?>>
{
	public DelegateMenuTypeCollection(String modid)
	{
		super(modid, Registry.MENU_REGISTRY);
	}

	public <C extends AbstractContainerMenu> ObjectRegistryHolder<MenuType<C>> add(String name, MenuFactory<C> factory)
	{
		return this.add(name, () -> RegistryHelpers.createMenuType(factory));
	}

	public <T extends BlockEntity, C extends AbstractContainerMenu> ObjectRegistryHolder<MenuType<C>> add(String name, TriFunction<Integer, Inventory, T, C> function)
	{
		return this.add(name, new MenuFactory<C>()
		{
			@Override
			public C create(int windowId, Inventory inv, FriendlyByteBuf data)
			{
				return packetParser(windowId, inv, data, function);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public <T extends BlockEntity, C extends AbstractContainerMenu> C packetParser(int windowId, Inventory inv, FriendlyByteBuf data, TriFunction<Integer, Inventory, T, C> function)
	{
		BlockEntity blockEntity = inv.player.getLevel().getBlockEntity(data.readBlockPos());
		return function.apply(windowId, inv, (T) blockEntity);
	}

}
