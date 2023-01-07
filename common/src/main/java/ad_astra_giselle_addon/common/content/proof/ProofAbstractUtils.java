package ad_astra_giselle_addon.common.content.proof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.entity.EntityCustomDataHelper;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.util.NBTUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public abstract class ProofAbstractUtils
{
	private static final List<ProofAbstractUtils> _PROOFS = new ArrayList<>();
	public static final List<ProofAbstractUtils> PROOFS = Collections.unmodifiableList(_PROOFS);

	public static final String KEY_PROOF_MAP = AdAstraGiselleAddon.rl("proof").toString();
	public static final String KEY_PROOF_DURATION = "proof_duration";

	public static final int GENERAL_PROOF_INTERVAL = 10;
	public static final int OXYGEN_PROOF_INTERVAL = 30;
	public static final long OXYGEN_PROOF_USING = FluidHooks2.MILLI_BUCKET;

	public static CompoundTag getAllCustomData(LivingEntity living)
	{
		return NBTUtils.getOrCreateTag(EntityCustomDataHelper.getCustomData(living), KEY_PROOF_MAP);
	}

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
	private final String customDataKey;

	public ProofAbstractUtils(ResourceLocation id)
	{
		this.id = id;
		this.customDataKey = id.toString();
		_PROOFS.add(this);
	}

	public ResourceLocation getId()
	{
		return this.id;
	}

	public String getCustomDataKey()
	{
		return this.customDataKey;
	}

	public CompoundTag getCustomData(LivingEntity living)
	{
		return NBTUtils.getTag(getAllCustomData(living), this.getCustomDataKey());
	}

	public CompoundTag getOrCreateData(LivingEntity living)
	{
		return NBTUtils.getOrCreateTag(getAllCustomData(living), this.getCustomDataKey());
	}

	public int getProofDuration(LivingEntity living)
	{
		return this.getCustomData(living).getInt(KEY_PROOF_DURATION);
	}

	public void setProofDuration(LivingEntity living, int proofDuration)
	{
		this.getOrCreateData(living).putInt(KEY_PROOF_DURATION, proofDuration);
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
