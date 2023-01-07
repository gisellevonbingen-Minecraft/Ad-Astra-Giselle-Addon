package ad_astra_giselle_addon.common.content.proof;

import java.util.List;
import java.util.function.Function;

import com.google.common.eventbus.Subscribe;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class AcidRainProofUtils extends ProofAbstractUtils
{
	public static final AcidRainProofUtils INSTANCE = new AcidRainProofUtils(AdAstraGiselleAddon.rl("acid_rain_proof"));

	public AcidRainProofUtils(ResourceLocation id)
	{
		super(id);
	}

	@Override
	public LivingProofProvidingEvent createEvent(LivingEntity entity, List<Function<LivingEntity, ProofSession>> functions)
	{
		return new LivingVenusAcidProofProvidingEvent(entity, functions);
	}

	@Subscribe
	public void onEnchantmentProviding(LivingVenusAcidProofProvidingEvent e)
	{
		e.add(living -> new AcidRainProofEnchantmentSession(living, AddonEnchantments.ACID_RAIN_PROOF.get()));
	}

}
