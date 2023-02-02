package ad_astra_giselle_addon.common.menu;

import java.util.Collections;

import ad_astra_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.networking.packet.server.MachineInfoPacket;
import earth.terrarium.ad_astra.common.screen.menu.AbstractMachineMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class GravityNormalizerMenu extends AbstractMachineMenu<GravityNormalizerBlockEntity>
{
	public GravityNormalizerMenu(int windowId, Inventory inv, GravityNormalizerBlockEntity blockEntity)
	{
		super(AddonMenuTypes.GRAVITY_NORMALIZER.get(), windowId, inv, blockEntity);
	}

	@Override
	public int getPlayerInventoryOffset()
	{
		return 16;
	}

	@Override
	public void syncClientScreen()
	{
		GravityNormalizerBlockEntity machine = this.getMachine();
		Player player = this.player;
		NetworkHandling.CHANNEL.sendToPlayer(new MachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), Collections.emptyList()), player);
	}

}
