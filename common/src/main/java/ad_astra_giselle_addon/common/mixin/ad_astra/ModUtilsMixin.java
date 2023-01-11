package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.content.proof.GravityNormalizingUtils;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mixin(value = ModUtils.class, remap = false)
public abstract class ModUtilsMixin
{
	@Inject(method = "getEntityGravity", at = @At("TAIL"), cancellable = true)
	private static void getEntityGravity(Entity entity, CallbackInfoReturnable<Float> callbackInfo)
	{
		float gravity = callbackInfo.getReturnValueF();

		if (entity instanceof LivingEntity living && gravity != 1.0F)
		{
			if (GravityNormalizingUtils.INSTANCE.tryProvideProof(living))
			{
				callbackInfo.setReturnValue(1.0F);
			}

		}

	}

}
