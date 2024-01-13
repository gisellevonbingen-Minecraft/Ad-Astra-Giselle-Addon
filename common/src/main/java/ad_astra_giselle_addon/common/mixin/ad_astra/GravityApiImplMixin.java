package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.content.proof.GravityNormalizingUtils;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.systems.GravityApiImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mixin(value = GravityApiImpl.class, remap = false)
public abstract class GravityApiImplMixin
{
	@Inject(method = "getGravity(Lnet/minecraft/world/entity/Entity;)F", at = @At("TAIL"), cancellable = true)
	private void getGravity(Entity entity, CallbackInfoReturnable<Float> callbackInfo)
	{
		float gravity = callbackInfo.getReturnValueF();

		if (entity instanceof LivingEntity living && gravity != PlanetConstants.EARTH_GRAVITY)
		{
			if (GravityNormalizingUtils.INSTANCE.tryProvideProof(living))
			{
				callbackInfo.setReturnValue(PlanetConstants.EARTH_GRAVITY);
			}

		}

	}

}
