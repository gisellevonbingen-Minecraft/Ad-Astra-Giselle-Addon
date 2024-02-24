package ad_astra_giselle_addon.common.content.proof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public abstract class ProofAbstractUtils
{
	private static final List<ProofAbstractUtils> _PROOFS = new ArrayList<>();
	public static final List<ProofAbstractUtils> PROOFS = Collections.unmodifiableList(_PROOFS);

	public static final String KEY_PROOF_DURATION = AdAstraGiselleAddon.rl("proof_duration").toString();

	public static final int GENERAL_PROOF_INTERVAL = 10;
	public static final int OXYGEN_PROOF_INTERVAL = 30;
	public static final long OXYGEN_PROOF_USING = FluidConstants.fromMillibuckets(1L);

	public static void reduceProofDuration(LivingEntity living)
	{
		for (ProofAbstractUtils proof : PROOFS)
		{
			int currentDuration = proof.getProofDuration(living);

			if (currentDuration > 0)
			{
				proof.setProofDuration(living, currentDuration - 1);
			}

		}

	}

	private final ResourceLocation id;
	private final String dataKey;
	private final List<ProofFunction> listeners;

	protected ProofAbstractUtils(ResourceLocation id)
	{
		this.id = id;
		this.dataKey = id.toString();
		this.listeners = new ArrayList<>();
		_PROOFS.add(this);
	}

	public ResourceLocation getId()
	{
		return this.id;
	}

	public String getDataKey()
	{
		return this.dataKey;
	}

	public int getProofDuration(LivingEntity living)
	{
		return ((LivingProofDurationAccessor) living).ad_astra_giselle_addon$getProofDuration(this);
	}

	public void setProofDuration(LivingEntity living, int proofDuration)
	{
		((LivingProofDurationAccessor) living).ad_astra_giselle_addon$setProofDuration(this, proofDuration);
	}

	public boolean tryProvideProof(LivingEntity living)
	{
		if (this.getProofDuration(living) > 0)
		{
			return true;
		}
		else
		{
			int proofDuration = this.post(living);

			if (proofDuration > 0)
			{
				this.setProofDuration(living, proofDuration);
				return true;
			}

			return false;
		}

	}

	public void register(ProofFunction event)
	{
		this.listeners.add(event);
	}

	public boolean unregister(ProofFunction event)
	{
		return this.listeners.remove(event);
	}

	public int post(LivingEntity living)
	{
		for (ProofFunction event : this.listeners)
		{
			int proofFunction = event.provide(living);

			if (proofFunction > 0)
			{
				return proofFunction;
			}

		}

		return 0;
	}

}
