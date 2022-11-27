package ad_astra_giselle_addon.common.registries;

import ad_astra_giselle_addon.common.util.TriFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeDeferredRegister extends DeferredRegisterWrapper<MenuType<?>>
{
	public MenuTypeDeferredRegister(String modid)
	{
		super(modid, ForgeRegistries.MENU_TYPES);
	}

	public <C extends AbstractContainerMenu> RegistryObject<MenuType<C>> register(String name, IContainerFactory<C> factory)
	{
		return this.register(name, () -> new MenuType<>(factory));
	}

	public <T extends BlockEntity, C extends AbstractContainerMenu> RegistryObject<MenuType<C>> register(String name, TriFunction<Integer, Inventory, T, C> function)
	{
		return register(name, new IContainerFactory<C>()
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
