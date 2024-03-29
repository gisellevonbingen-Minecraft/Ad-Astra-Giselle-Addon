package ad_astra_giselle_addon.common.content.proof;

import earth.terrarium.adastra.api.events.AdAstraEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class OxygenProofUtils extends ProofAbstractUtils
{
	public OxygenProofUtils(ResourceLocation id)
	{
		super(id);

		AdAstraEvents.EntityOxygenEvent.register(this::onEntityOxygen);
		AdAstraEvents.ColdTemperatureTickEvent.register(this::onColdTemperatureTick);
	}

	private boolean onEntityOxygen(Entity entity, boolean hasOxygen)
	{
		if (!hasOxygen && entity instanceof LivingEntity living)
		{
			if (this.tryProvideProof(living))
			{
				return true;
			}

		}

		return hasOxygen;
	}

	private boolean onColdTemperatureTick(ServerLevel level, LivingEntity living)
	{
		if (this.tryProvideProof(living))
		{
			return false;
		}

		return true;
	}

}
