package ad_astra_giselle_addon.common.menu;

import ad_astra_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import net.minecraft.world.entity.player.Inventory;

public class GravityNormalizerMenu extends AddonMachineMenu<GravityNormalizerBlockEntity>
{
	public GravityNormalizerMenu(int windowId, Inventory inv, GravityNormalizerBlockEntity blockEntity)
	{
		super(AddonMenuTypes.GRAVITY_NORMALIZER.get(), windowId, inv, blockEntity);
	}

	@Override
	protected void addMenuSlots()
	{
		super.addMenuSlots();
	}

	@Override
	protected void addConfigSlots()
	{
		GravityNormalizerBlockEntity blockEntity = this.getEntity();
		this.addConfigSlot(new EnergyConfiguration(0, 146, 22, blockEntity.getEnergyStorage()));
	}

	@Override
	public int startIndex()
	{
		return 1;
	}

	@Override
	public int getContainerInputEnd()
	{
		return 1;
	}

	@Override
	public int getInventoryStart()
	{
		return 1;
	}

	@Override
	public int getPlayerInvXOffset()
	{
		return 8;
	}

	@Override
	public int getPlayerInvYOffset()
	{
		return 114;
	}

}
