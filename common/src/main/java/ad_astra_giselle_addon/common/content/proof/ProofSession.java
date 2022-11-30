package ad_astra_giselle_addon.common.content.proof;

import net.minecraft.world.entity.LivingEntity;

public abstract class ProofSession
{
	private LivingEntity living;

	public ProofSession(LivingEntity living)
	{
		this.living = living;
	}

	public boolean canProvide()
	{
		return true;
	}

	public void onProvide()
	{

	}

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

	public abstract int getProofDuration();

	public LivingEntity getLiving()
	{
		return this.living;
	}

}
