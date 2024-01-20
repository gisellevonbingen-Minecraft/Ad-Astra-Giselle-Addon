package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ad_astra_giselle_addon.common.registry.AddonProofs;
import earth.terrarium.ad_astra.common.entity.system.EntityAcidRainSystem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

@Mixin(value = EntityAcidRainSystem.class, remap = false)
public abstract class EntityAcidRainSystemMixin
{
	@Inject(method = "causeDamage", at = @At("HEAD"), cancellable = true)
	private static void causeDamage(LivingEntity entity, ServerLevel level, CallbackInfo callbackInfo)
	{
		if (AddonProofs.ACID_RAIN.tryProvideProof(entity))
		{
			callbackInfo.cancel();
		}

	}

}
