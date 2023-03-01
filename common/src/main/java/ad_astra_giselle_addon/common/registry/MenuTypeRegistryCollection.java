package ad_astra_giselle_addon.common.registry;

import java.util.function.Supplier;

import ad_astra_giselle_addon.common.util.TriFunction;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import earth.terrarium.botarium.common.registry.RegistryHelpers.MenuFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MenuTypeRegistryCollection extends ObjectRegistryCollection<MenuType<?>>
{
	public MenuTypeRegistryCollection(String modid)
	{
		super(modid, Registries.MENU);
	}

	private <MENU extends AbstractContainerMenu> Supplier<? extends MenuType<MENU>> getBuilder(MenuFactory<MENU> factory)
	{
		return () -> RegistryHelpers.createMenuType(factory);
	}

	public <MENU extends AbstractContainerMenu> MenuTypeRegistryHolder<MENU> add(String name, MenuFactory<MENU> factory)
	{
		return this.add(name, this.getBuilder(factory), MenuTypeRegistryHolder<MENU>::new);
	}

	public <T extends BlockEntity, C extends AbstractContainerMenu> MenuTypeRegistryHolder<C> add(String name, TriFunction<Integer, Inventory, T, C> function)
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
	public <BE extends BlockEntity, MENU extends AbstractContainerMenu> MENU packetParser(int windowId, Inventory inv, FriendlyByteBuf data, TriFunction<Integer, Inventory, BE, MENU> function)
	{
		BlockEntity blockEntity = inv.player.getLevel().getBlockEntity(data.readBlockPos());
		return function.apply(windowId, inv, (BE) blockEntity);
	}

}
