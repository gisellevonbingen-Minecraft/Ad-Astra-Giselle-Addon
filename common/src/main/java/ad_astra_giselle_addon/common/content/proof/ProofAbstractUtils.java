package ad_astra_giselle_addon.common.content.proof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public abstract class ProofAbstractUtils
{
	private static final List<ProofAbstractUtils> _PROOFS = new ArrayList<>();
	public static final List<ProofAbstractUtils> PROOFS = Collections.unmodifiableList(_PROOFS);

	public static final String KEY_PROOF_DURATION = AdAstraGiselleAddon.rl("proof_duration").toString();

	public static final int GENERAL_PROOF_INTERVAL = 10;
	public static final int OXYGEN_PROOF_INTERVAL = 30;
	public static final long OXYGEN_PROOF_USING = FluidAmounts.toPlatformAmount(1L);

	public static void reduceProofDuration(Entity entity)
	{
		for (ProofAbstractUtils proof : PROOFS)
		{
			int currentDuration = proof.getProofDuration(entity);

			if (currentDuration > 0)
			{
				proof.setProofDuration(entity, currentDuration - 1);
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

	public int getProofDuration(Entity entity)
	{
		return ((EntityProofDurationAccessor) entity).ad_astra_giselle_addon$getProofDuration(this);
	}

	public void setProofDuration(Entity entity, int proofDuration)
	{
		((EntityProofDurationAccessor) entity).ad_astra_giselle_addon$setProofDuration(this, proofDuration);
	}

	public boolean tryProvideProof(Entity entity)
	{
		if (this.getProofDuration(entity) > 0)
		{
			return true;
		}
		else
		{
			int proofDuration = this.post(entity);

			if (proofDuration > 0)
			{
				this.setProofDuration(entity, proofDuration);
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

	public int post(Entity entity)
	{
		for (ProofFunction event : this.listeners)
		{
			int proofFunction = event.provide(entity);

			if (proofFunction > 0)
			{
				return proofFunction;
			}

		}

		return 0;
	}

}
