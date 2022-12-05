package ad_astra_giselle_addon.common.content.proof;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.eventbus.Subscribe;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registries.AddonAttributes;
import ad_astra_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class SpaceFireProofUtils extends ProofAbstractUtils
{
	public static final SpaceFireProofUtils INSTANCE = new SpaceFireProofUtils(AdAstraGiselleAddon.rl("space_fire_proof"), AddonAttributes.SPACE_FIRE_PROOF_DURATION);

	public SpaceFireProofUtils(ResourceLocation id, Supplier<Attribute> attribute)
	{
		super(id, attribute);
	}

	@Override
	public LivingProofProvidingEvent createEvent(LivingEntity entity, List<Function<LivingEntity, ProofSession>> functions)
	{
		return new LivingSpaceFireProofProvidingEvent(entity, functions);
	}

	@Subscribe
	public void onEnchantmentProviding(LivingSpaceFireProofProvidingEvent e)
	{
		e.add(living -> new SpaceFireProofEnchantmentSession(living, AddonEnchantments.SPACE_FIRE_PROOF.get()));
	}

}
