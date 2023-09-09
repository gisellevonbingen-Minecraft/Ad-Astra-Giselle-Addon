package ad_astra_giselle_addon.common.menu;

import ad_astra_giselle_addon.common.block.entity.RocketSensorBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class RocketSensorMenu extends AbstractMachineMenu<RocketSensorBlockEntity>
{
	public RocketSensorMenu(int windowId, Inventory inv, RocketSensorBlockEntity blockEntity)
	{
		super(AddonMenuTypes.ROCKET_SENSOR.get(), windowId, inv, blockEntity, new Slot[0]);
	}

	@Override
	protected void setPlayerInventory(Inventory inventory)
	{

	}

	@Override
	public void syncClientScreen()
	{

	}

}
