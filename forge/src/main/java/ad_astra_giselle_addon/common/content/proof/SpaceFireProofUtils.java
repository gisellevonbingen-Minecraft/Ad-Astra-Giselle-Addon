package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpaceFireProofUtils extends ProofAbstractUtils
{
	public static final String NBT_KEY = AdAstraGiselleAddon.rl("space_fire_proof").toString();
	public static final SpaceFireProofUtils INSTANCE = new SpaceFireProofUtils();

	private SpaceFireProofUtils()
	{

	}

	@Override
	public String getNBTKey()
	{
		return NBT_KEY;
	}

	@Override
	public LivingProofEvent createEvent(LivingEntity entity)
	{
		return new LivingSpaceFireProofEvent(entity);
	}

	@SubscribeEvent
	public void onProofEnchantment(LivingSpaceFireProofEvent e)
	{
		LivingEntity entity = e.getEntity();
		ProofSession session = new SpaceFireProofEnchantmentSession(entity, AddonEnchantments.SPACE_FIRE_PROOF.get());
		e.setProofDuration(session.provide());
	}

}
