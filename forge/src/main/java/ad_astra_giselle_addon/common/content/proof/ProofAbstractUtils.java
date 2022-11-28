package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.util.NBTUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class ProofAbstractUtils
{
	public static final String NBT_PROOF_DURATION_KEY = "proof_duration";

	public static void register(IEventBus bus)
	{
		bus.register(SpaceOxygenProofUtils.INSTANCE);
		bus.register(SpaceFireProofUtils.INSTANCE);
		bus.register(VenusAcidProofUtils.INSTANCE);
	}

	public ProofAbstractUtils()
	{

	}

	@SubscribeEvent
	public void onLivingUpdate(LivingTickEvent e)
	{
		LivingEntity entity = e.getEntity();
		this.reduceProofDuration(entity);
	}

	public void reduceProofDuration(LivingEntity entity)
	{
		int proofDuration = this.getProofDuration(entity);

		if (proofDuration > 0)
		{
			this.setProofDuration(entity, proofDuration - 1);
		}

	}

	public int getProofDuration(LivingEntity entity)
	{
		CompoundTag compound = NBTUtils.getTag(entity, this.getNBTKey());
		return compound != null ? compound.getInt(NBT_PROOF_DURATION_KEY) : 0;
	}

	public void setProofDuration(LivingEntity entity, int proofDuration)
	{
		CompoundTag compound = NBTUtils.getOrCreateTag(entity, this.getNBTKey());
		compound.putLong(NBT_PROOF_DURATION_KEY, Math.max(proofDuration, 0));
	}

	public boolean tryProvideProof(EntityEvent e)
	{
		if (e.isCanceled())
		{
			return false;
		}
		else if (e.getEntity() instanceof LivingEntity living)
		{
			return this.tryProvideProof(living);
		}
		else
		{
			return false;
		}

	}

	public boolean tryProvideProof(LivingEvent e)
	{
		if (e.isCanceled())
		{
			return false;
		}
		else
		{
			return this.tryProvideProof(e.getEntity());
		}

	}

	public boolean tryProvideProof(LivingEntity entity)
	{
		if (this.getProofDuration(entity) > 0)
		{
			return true;
		}
		else
		{
			LivingProofEvent event = this.createEvent(entity);

			if (event != null)
			{
				LivingProofEvent.postUntilDuration(event);
				int proofDuration = event.getProofDuration();

				if (proofDuration > 0)
				{
					this.setProofDuration(entity, proofDuration);
					return true;
				}

			}

			return false;
		}

	}

	public abstract LivingProofEvent createEvent(LivingEntity entity);

	public abstract String getNBTKey();

}
