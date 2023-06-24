package ad_astra_giselle_addon.common.menu;

import java.util.Collections;

import ad_astra_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonMenuTypes;
import earth.terrarium.ad_astra.common.networking.NetworkHandler;
import earth.terrarium.ad_astra.common.networking.packet.messages.ClientboundMachineInfoPacket;
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
		return 30;
	}

	@Override
	public void syncClientScreen()
	{
		GravityNormalizerBlockEntity machine = this.getMachine();
		Player player = this.player;
		NetworkHandler.CHANNEL.sendToPlayer(new ClientboundMachineInfoPacket(machine.getEnergyStorage().getStoredEnergy(), Collections.emptyList()), player);
	}

}
