package ad_astra_giselle_addon.common.content.proof;

import java.util.List;
import java.util.function.Function;

import com.google.common.eventbus.Subscribe;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class VenusAcidProofUtils extends ProofAbstractUtils
{
	public static final VenusAcidProofUtils INSTANCE = new VenusAcidProofUtils(AdAstraGiselleAddon.rl("venus_acid_proof"));

	public VenusAcidProofUtils(ResourceLocation id)
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
		e.add(living -> new VenusAcidProofEnchantmentSession(living, AddonEnchantments.VENUS_ACID_PROOF.get()));
	}

}
