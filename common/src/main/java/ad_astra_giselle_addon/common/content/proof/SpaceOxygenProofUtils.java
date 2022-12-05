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

public class SpaceOxygenProofUtils extends ProofAbstractUtils
{
	public static final SpaceOxygenProofUtils INSTANCE = new SpaceOxygenProofUtils(AdAstraGiselleAddon.rl("space_breathing"), AddonAttributes.SPACE_OXYGEN_PROOF_DURATION);

	public SpaceOxygenProofUtils(ResourceLocation id, Supplier<Attribute> attribute)
	{
		super(id, attribute);
	}

	@Override
	public LivingProofProvidingEvent createEvent(LivingEntity entity, List<Function<LivingEntity, ProofSession>> functions)
	{
		return new LivingSpaceOxygenProofProvidingEvent(entity, functions);
	}

	@Subscribe
	public void onProofEnchantment(LivingSpaceOxygenProofProvidingEvent e)
	{
		e.add(living -> new SpaceOxygenProofEnchantmentSession(living, AddonEnchantments.SPACE_BREATHING.get()));
	}

}
