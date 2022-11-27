package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import earth.terrarium.ad_astra.registry.ModDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpaceOxygenProofUtils extends ProofAbstractUtils
{
	public static final String NBT_KEY = AdAstraGiselleAddon.rl("space_breathing").toString();
	public static final SpaceOxygenProofUtils INSTANCE = new SpaceOxygenProofUtils();

	private SpaceOxygenProofUtils()
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
		return new LivingSpaceOxygenProofEvent(entity);
	}

	@SubscribeEvent
	public void onProofEnchantment(LivingSpaceOxygenProofEvent e)
	{
		LivingEntity entity = e.getEntity();
		ProofSession session = new SpaceOxygenProofEnchantmentSession(entity, AddonEnchantments.SPACE_BREATHING.get());
		e.setProofDuration(session.provide());
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e)
	{
		if (e.getSource() == ModDamageSource.OXYGEN && this.tryProvideProof(e))
		{
			e.setCanceled(true);
		}

	}

}
