package ad_astra_giselle_addon.client.compat.jei;

import java.util.List;
import java.util.Optional;

import ad_astra_giselle_addon.common.menu.AddonMachineMenu;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public class AddonMachineTransferInfo<MENU extends AddonMachineMenu<? extends ContainerMachineBlockEntity>, RECIPE> implements IRecipeTransferInfo<MENU, RECIPE>
{
	private final Class<MENU> menuClass;
	private final MenuType<MENU> menuType;
	private final RecipeType<RECIPE> recipeType;

	public AddonMachineTransferInfo(Class<MENU> menuClass, MenuType<MENU> menuType, RecipeType<RECIPE> recipeType)
	{
		this.menuClass = menuClass;
		this.menuType = menuType;
		this.recipeType = recipeType;
	}

	@Override
	public Class<? extends MENU> getContainerClass()
	{
		return this.menuClass;
	}

	@Override
	public Optional<MenuType<MENU>> getMenuType()
	{
		return Optional.ofNullable(this.menuType);
	}

	@Override
	public RecipeType<RECIPE> getRecipeType()
	{
		return this.recipeType;
	}

	@Override
	public boolean canHandle(MENU container, RECIPE recipe)
	{
		return true;
	}

	@Override
	public List<Slot> getRecipeSlots(MENU menu, RECIPE recipe)
	{
		return menu.slots.subList(menu.startIndex(), menu.getContainerInputEnd());
	}

	@Override
	public List<Slot> getInventorySlots(MENU menu, RECIPE recipe)
	{
		int inventoryStart = menu.getInventoryStart();
		return menu.slots.subList(menu.getInventoryStart(), inventoryStart + 36);
	}

}
