package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import earth.terrarium.adastra.common.systems.OxygenApiImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mixin(value = OxygenApiImpl.class, remap = false)
public abstract class OxygenApiImplMixin
{
	@Inject(method = "hasOxygen(Lnet/minecraft/world/entity/Entity;)Z", at = @At("TAIL"), cancellable = true)
	private void hasOxygen(Entity entity, CallbackInfoReturnable<Boolean> callbackInfo)
	{
		boolean hasOxygen = callbackInfo.getReturnValueZ();

		if (!hasOxygen && entity instanceof LivingEntity living)
		{
			if (SpaceOxygenProofUtils.INSTANCE.tryProvideProof(living))
			{
				callbackInfo.setReturnValue(true);
			}

		}

	}

}
