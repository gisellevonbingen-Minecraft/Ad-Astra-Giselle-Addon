package ad_astra_giselle_addon.common.content.proof;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.entity.IProofDurationAccesor;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public abstract class ProofAbstractUtils
{
	public static final int GENERAL_PROOF_INTERVAL = 10;
	public static final int OXYGEN_PROOF_INTERVAL = 30;
	public static final long OXYGEN_PROOF_USING = FluidHooks2.MB_1;

	private ResourceLocation id;

	public ProofAbstractUtils(ResourceLocation id)
	{
		this.id = id;
	}

	public ResourceLocation getId()
	{
		return this.id;
	}

	public int getProofDuration(LivingEntity living)
	{
		if (living instanceof IProofDurationAccesor accessor)
		{
			return accessor.getProofDuration(this);
		}
		else
		{
			return 0;
		}

	}

	public void setProofDuration(LivingEntity living, int proofDuration)
	{
		if (living instanceof IProofDurationAccesor accessor)
		{
			accessor.setPoofDuration(this, proofDuration);
		}

	}

	public boolean tryProvideProof(LivingEntity living)
	{
		if (this.getProofDuration(living) > 0)
		{
			return true;
		}
		else
		{
			List<Function<LivingEntity, ProofSession>> list = new ArrayList<>();
			LivingProofProvidingEvent event = this.createEvent(living, list);
			AdAstraGiselleAddon.eventBus().post(event);

			for (Function<LivingEntity, ProofSession> function : list)
			{
				ProofSession session = function.apply(living);

				if (session == null)
				{
					continue;
				}

				int proofDuration = session.provide();

				if (proofDuration <= 0)
				{
					continue;
				}
				else
				{
					this.setProofDuration(living, proofDuration);
					return true;
				}

			}

			return false;
		}

	}

	public abstract LivingProofProvidingEvent createEvent(LivingEntity entity, List<Function<LivingEntity, ProofSession>> function);
}
