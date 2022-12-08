package ad_astra_giselle_addon.common.content.proof;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.eventbus.Subscribe;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registry.AddonAttributes;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AcidRainProofUtils extends ProofAbstractUtils
{
	public static final AcidRainProofUtils INSTANCE = new AcidRainProofUtils(AdAstraGiselleAddon.rl("acid_rain_proof"), AddonAttributes.ACID_RAIN_PROOF_DURATION);

	public AcidRainProofUtils(ResourceLocation id, Supplier<Attribute> attribute)
	{
		super(id, attribute);
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
