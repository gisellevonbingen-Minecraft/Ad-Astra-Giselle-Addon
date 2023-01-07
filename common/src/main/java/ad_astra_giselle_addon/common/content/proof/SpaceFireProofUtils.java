package ad_astra_giselle_addon.common.content.proof;

import java.util.List;
import java.util.function.Function;

import com.google.common.eventbus.Subscribe;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class SpaceFireProofUtils extends ProofAbstractUtils
{
	public static final SpaceFireProofUtils INSTANCE = new SpaceFireProofUtils(AdAstraGiselleAddon.rl("space_fire_proof"));

	public SpaceFireProofUtils(ResourceLocation id)
	{
		super(id);
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
