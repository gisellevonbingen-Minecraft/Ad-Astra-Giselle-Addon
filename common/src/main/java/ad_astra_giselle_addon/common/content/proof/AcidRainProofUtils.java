package ad_astra_giselle_addon.common.content.proof;

import earth.terrarium.adastra.api.events.AdAstraEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class AcidRainProofUtils extends ProofAbstractUtils
{
	public AcidRainProofUtils(ResourceLocation id)
	{
		super(id);

		AdAstraEvents.AcidRainTickEvent.register(this::onAcidRainTick);
	}

	private boolean onAcidRainTick(ServerLevel level, LivingEntity living)
	{
		if (this.tryProvideProof(living))
		{
			return false;
		}

		return true;
	}

}
