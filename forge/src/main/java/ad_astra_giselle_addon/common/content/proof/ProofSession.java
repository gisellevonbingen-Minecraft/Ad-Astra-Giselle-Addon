package ad_astra_giselle_addon.common.content.proof;

import net.minecraft.world.entity.LivingEntity;

public abstract class ProofSession
{
	private LivingEntity entity;

	public ProofSession(LivingEntity entity)
	{
		this.entity = entity;
	}

	public boolean canProvide()
	{
		return true;
	}

	public void onProvide()
	{

	}

	public abstract int getProofDuration();

	public int provide()
	{
		if (this.canProvide())
		{
			this.onProvide();

			int proofDuration = this.getProofDuration();
			return proofDuration;
		}
		else
		{
			return 0;
		}

	}

	public LivingEntity getEntity()
	{
		return this.entity;
	}

}
