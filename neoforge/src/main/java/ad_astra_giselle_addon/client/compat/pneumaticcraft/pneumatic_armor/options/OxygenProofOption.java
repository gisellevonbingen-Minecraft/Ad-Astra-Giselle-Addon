package ad_astra_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.options;

import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IArmorUpgradeClientHandler;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IGuiScreen;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IOptionPage.SimpleOptionPage;
import me.desht.pneumaticcraft.client.pneumatic_armor.ClientArmorRegistry;

public class OxygenProofOption<T extends IArmorUpgradeClientHandler<?>> extends SimpleOptionPage<T>
{
	public OxygenProofOption(IGuiScreen screen, T clientUpgradeHandler)
	{
		super(screen, clientUpgradeHandler);
	}

	@Override
	public void populateGui(IGuiScreen gui)
	{
		super.populateGui(gui);

		gui.addWidget(ClientArmorRegistry.getInstance().makeStatMoveButton(30, 128, this.getClientUpgradeHandler()));
	}

}
