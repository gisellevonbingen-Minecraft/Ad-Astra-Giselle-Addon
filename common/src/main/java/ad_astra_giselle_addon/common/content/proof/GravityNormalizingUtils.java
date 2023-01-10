package ad_astra_giselle_addon.common.content.proof;

import java.util.List;
import java.util.function.Function;

import com.google.common.eventbus.Subscribe;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class GravityNormalizingUtils extends ProofAbstractUtils
{
	public static final GravityNormalizingUtils INSTANCE = new GravityNormalizingUtils(AdAstraGiselleAddon.rl("gravity_noramlizing"));

	public GravityNormalizingUtils(ResourceLocation id)
	{
		super(id);
	}

	@Override
	public LivingProofProvidingEvent createEvent(LivingEntity entity, List<Function<LivingEntity, ProofSession>> functions)
	{
		return new LivingGravityNormalizingProvidingEvent(entity, functions);
	}

	@Subscribe
	public void onEnchantmentProviding(LivingGravityNormalizingProvidingEvent e)
	{
		e.add(living -> new GravityNormalizingEnchantmentSession(living, AddonEnchantments.GRAVITY_NORMALIZING.get()));
	}

}
