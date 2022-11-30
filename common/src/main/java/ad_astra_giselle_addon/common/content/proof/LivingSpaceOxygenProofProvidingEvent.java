package ad_astra_giselle_addon.common.content.proof;

import java.util.List;
import java.util.function.Function;

import net.minecraft.world.entity.LivingEntity;

public class LivingSpaceOxygenProofProvidingEvent extends LivingProofProvidingEvent
{
	public LivingSpaceOxygenProofProvidingEvent(LivingEntity living, List<Function<LivingEntity, ProofSession>> functions)
	{
		super(living, functions);
	}

}
