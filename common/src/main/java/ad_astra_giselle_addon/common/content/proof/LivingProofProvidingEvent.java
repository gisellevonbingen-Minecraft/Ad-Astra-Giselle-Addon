package ad_astra_giselle_addon.common.content.proof;

import java.util.List;
import java.util.function.Function;

import net.minecraft.world.entity.LivingEntity;

public abstract class LivingProofProvidingEvent
{
	private final LivingEntity living;
	private final List<Function<LivingEntity, ProofSession>> functions;

	public LivingProofProvidingEvent(LivingEntity living, List<Function<LivingEntity, ProofSession>> functions)
	{
		this.living = living;
		this.functions = functions;
	}

	public void add(Function<LivingEntity, ProofSession> function)
	{
		this.functions.add(function);
	}

	public LivingEntity getLiving()
	{
		return this.living;
	}

}
