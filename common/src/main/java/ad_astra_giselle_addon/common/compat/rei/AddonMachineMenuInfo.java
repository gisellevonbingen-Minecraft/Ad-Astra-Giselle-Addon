package ad_astra_giselle_addon.common.compat.rei;

import java.util.function.Function;

import ad_astra_giselle_addon.common.menu.AddonMachineMenu;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimplePlayerInventoryMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;

public class AddonMachineMenuInfo<MENU extends AddonMachineMenu<?>, DISPLAY extends Display> implements SimplePlayerInventoryMenuInfo<MENU, DISPLAY>
{
	private final DISPLAY display;
	private final Function<DISPLAY, Recipe<?>> display2RecipeFunc;

	public AddonMachineMenuInfo(DISPLAY display, Function<DISPLAY, Recipe<?>> display2RecipeFunc)
	{
		this.display = display;
		this.display2RecipeFunc = display2RecipeFunc;
	}

	@Override
	public DISPLAY getDisplay()
	{
		return this.display;
	}

	@Override
	public void markDirty(MenuInfoContext<MENU, ? extends ServerPlayer, DISPLAY> context)
	{
		SimplePlayerInventoryMenuInfo.super.markDirty(context);
		context.getMenu().onRecipeTransfer(this.display2RecipeFunc.apply(context.getDisplay()));
	}

	@Override
	public Iterable<SlotAccessor> getInputSlots(MenuInfoContext<MENU, ?, DISPLAY> context)
	{
		MENU menu = context.getMenu();
		return menu.slots.subList(menu.startIndex(), menu.getContainerInputEnd()).stream().map(SlotAccessor::fromSlot).toList();
	}

}
