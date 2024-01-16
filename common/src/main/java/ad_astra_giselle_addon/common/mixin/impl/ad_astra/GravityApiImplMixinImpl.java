package ad_astra_giselle_addon.common.mixin.impl.ad_astra;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.content.proof.GravityNormalizingUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class GravityApiImplMixinImpl
{
	public static void getGravity(Entity entity, CallbackInfoReturnable<Float> cir)
	{
		float gravity = cir.getReturnValueF();

		if (entity instanceof LivingEntity living && gravity != 1.0F)
		{
			if (GravityNormalizingUtils.INSTANCE.tryProvideProof(living))
			{
				cir.setReturnValue(1.0F);
			}

		}

	}

}
