package ad_astra_giselle_addon.client.compat.tconstruct;

import ad_astra_giselle_addon.client.overlay.OxygenCanOverlay;
import ad_astra_giselle_addon.common.compat.tconstruct.AddonTConstructModifiers;
import ad_astra_giselle_addon.common.compat.tconstruct.TConstructProofProvidingHandler;

public class TConstructCompatClient
{
	public TConstructCompatClient()
	{
		OxygenCanOverlay.SHOULD_RENDER_EVENT.register(player -> TConstructProofProvidingHandler.findFirst(player, AddonTConstructModifiers.OXYGEN_PROOF.get()) != null);
	}

}
