package ad_astra_giselle_addon.common.content.proof;

import earth.terrarium.adastra.api.events.AdAstraEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class GravityProofUtils extends ProofAbstractUtils
{
	public GravityProofUtils(ResourceLocation id)
	{
		super(id);

		AdAstraEvents.EntityGravityEvent.register(this::onEntityGravity);
	}

	private float onEntityGravity(Entity entity, float gravity)
	{
		if (gravity != 1.0F && entity instanceof LivingEntity living)
		{
			if (this.tryProvideProof(living))
			{
				return 1.0F;
			}

		}

		return gravity;
	}

}
