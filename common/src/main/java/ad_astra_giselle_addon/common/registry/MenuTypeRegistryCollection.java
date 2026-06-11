package ad_astra_giselle_addon.common.registry;

import java.util.Optional;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.teamresourceful.resourcefullib.common.menu.MenuContent;
import com.teamresourceful.resourcefullib.common.menu.MenuContentHelper;
import com.teamresourceful.resourcefullib.common.menu.MenuContentHelper.MenuFactory;
import com.teamresourceful.resourcefullib.common.menu.MenuContentSerializer;

import ad_astra_giselle_addon.common.util.TriFunction;
import earth.terrarium.adastra.common.menus.content.PositionContent;
import net.minecraft.core.registries.Registries;
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

	private <MENU extends AbstractContainerMenu, CONTENT extends MenuContent<CONTENT>> Supplier<? extends MenuType<MENU>> getBuilder(MenuFactory<MENU, CONTENT> factory, MenuContentSerializer<CONTENT> serializer)
	{
		return () -> MenuContentHelper.create(factory, serializer);
	}

	public <MENU extends AbstractContainerMenu, CONTENT extends MenuContent<CONTENT>> MenuTypeRegistryHolder<MENU> add(String name, MenuFactory<MENU, CONTENT> factory, MenuContentSerializer<CONTENT> serializer)
	{
		return this.add(name, this.getBuilder(factory, serializer), MenuTypeRegistryHolder<MENU>::new);
	}

	public <T extends BlockEntity, C extends AbstractContainerMenu> MenuTypeRegistryHolder<C> add(String name, TriFunction<Integer, Inventory, T, C> function)
	{
		return this.add(name, new MenuFactory<C, PositionContent>()
		{
			@Override
			public @NotNull C create(int windowId, Inventory inv, Optional<PositionContent> content)
			{
				return packetParser(windowId, inv, content, function);
			}
		}, PositionContent.SERIALIZER);
	}

	@SuppressWarnings("unchecked")
	public <BE extends BlockEntity, MENU extends AbstractContainerMenu> MENU packetParser(int windowId, Inventory inv, Optional<PositionContent> content, TriFunction<Integer, Inventory, BE, MENU> function)
	{
		BlockEntity blockEntity = inv.player.level().getBlockEntity(content.get().pos());
		return function.apply(windowId, inv, (BE) blockEntity);
	}

}
