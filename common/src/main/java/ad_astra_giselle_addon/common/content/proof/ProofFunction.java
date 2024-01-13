package ad_astra_giselle_addon.common.content.proof;

import net.minecraft.world.entity.LivingEntity;

@FunctionalInterface
public interface ProofFunction
{
	int provide(LivingEntity living);
}
