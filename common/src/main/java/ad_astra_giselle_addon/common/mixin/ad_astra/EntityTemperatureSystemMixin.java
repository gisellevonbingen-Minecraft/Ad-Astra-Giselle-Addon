package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ad_astra_giselle_addon.common.content.proof.SpaceFireProofUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import earth.terrarium.ad_astra.common.entity.system.EntityTemperatureSystem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

@Mixin(value = EntityTemperatureSystem.class, remap = false)
public abstract class EntityTemperatureSystemMixin
{
	@Inject(method = "burnEntity", at = @At("HEAD"), cancellable = true)
	private static void burnEntity(LivingEntity entity, CallbackInfo callbackInfo)
	{
		if (SpaceFireProofUtils.INSTANCE.tryProvideProof(entity))
		{
			callbackInfo.cancel();
		}

	}

	@Inject(method = "freezeEntity", at = @At("HEAD"), cancellable = true)
	private static void freezeEntity(LivingEntity entity, ServerLevel level, CallbackInfo callbackInfo)
	{
		if (SpaceOxygenProofUtils.INSTANCE.tryProvideProof(entity))
		{
			callbackInfo.cancel();
		}

	}

}
