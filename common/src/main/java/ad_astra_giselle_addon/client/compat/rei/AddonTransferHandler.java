package ad_astra_giselle_addon.client.compat.rei;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ad_astra_giselle_addon.common.menu.AddonMachineMenu;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import net.minecraft.world.inventory.MenuType;

public class AddonTransferHandler<MENU extends AddonMachineMenu<? extends ContainerMachineBlockEntity>> implements SimpleTransferHandler
{
	private final MenuType<MENU> menuType;

	public AddonTransferHandler(MenuType<MENU> menuType)
	{
		this.menuType = menuType;
	}

	@Override
	public ApplicabilityResult checkApplicable(Context context)
	{
		if (context.getMenu().getType() == this.menuType)
		{
			return ApplicabilityResult.createApplicable();
		}
		else
		{
			return ApplicabilityResult.createNotApplicable();
		}

	}

	@Override
	public Iterable<SlotAccessor> getInputSlots(Context context)
	{
		var menu = (AddonMachineMenu<?>) context.getMenu();
		return menu.slots.subList(menu.startIndex(), menu.getContainerInputEnd()).stream().map(SlotAccessor::fromSlot).toList();
	}

	@Override
	public Iterable<SlotAccessor> getInventorySlots(Context context)
	{
		var minecraft = context.getMinecraft();
		var player = minecraft.player;
		var inventory = player.getInventory();
		return IntStream.range(0, inventory.items.size()).mapToObj(index -> SlotAccessor.fromPlayerInventory(player, index)).collect(Collectors.toList());
	}

	public MenuType<MENU> getMenuType()
	{
		return this.menuType;
	}

}
