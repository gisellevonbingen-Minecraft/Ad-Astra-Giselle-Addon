package ad_astra_giselle_addon.common.menu;

import org.jetbrains.annotations.Nullable;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.SideConfigurable;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class AddonMachineMenu<T extends BlockEntity & SideConfigurable & Container> extends MachineMenu<T>
{
	public AddonMachineMenu(@Nullable MenuType<?> type, int id, Inventory inventory, T entity)
	{
		super(type, id, inventory, entity);
	}

	@Override
	public abstract int startIndex();

	@Override
	public abstract int getContainerInputEnd();

	@Override
	public abstract int getInventoryStart();
}
