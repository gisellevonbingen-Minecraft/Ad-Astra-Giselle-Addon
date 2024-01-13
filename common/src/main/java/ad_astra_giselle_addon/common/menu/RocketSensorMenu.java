package ad_astra_giselle_addon.common.menu;

import ad_astra_giselle_addon.common.block.entity.RocketSensorBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import net.minecraft.world.entity.player.Inventory;

public class RocketSensorMenu extends AddonMachineMenu<RocketSensorBlockEntity>
{
	public RocketSensorMenu(int windowId, Inventory inv, RocketSensorBlockEntity blockEntity)
	{
		super(AddonMenuTypes.ROCKET_SENSOR.get(), windowId, inv, blockEntity);
	}

	@Override
	protected void addMenuSlots()
	{

	}

	@Override
	protected void addConfigSlots()
	{

	}

	@Override
	public int startIndex()
	{
		return 0;
	}

	@Override
	public int getContainerInputEnd()
	{
		return 0;
	}

	@Override
	public int getInventoryStart()
	{
		return 1;
	}

	@Override
	public int getPlayerInvXOffset()
	{
		return super.getPlayerInvXOffset();
	}

	@Override
	public int getPlayerInvYOffset()
	{
		return 130;
	}

}
