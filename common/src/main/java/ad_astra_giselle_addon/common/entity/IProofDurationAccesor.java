package ad_astra_giselle_addon.common.entity;

import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;

public interface IProofDurationAccesor
{
	public int getProofDuration(ProofAbstractUtils proof);

	public void setPoofDuration(ProofAbstractUtils proof, int duration);
}
