package ad_astra_giselle_addon.common.mixin.impl.ad_astra;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class OxygenApiImplMixinImpl
{
	public static void hasOxygen(Entity entity, CallbackInfoReturnable<Boolean> cir)
	{
		boolean hasOxygen = cir.getReturnValueZ();

		if (!hasOxygen && entity instanceof LivingEntity living)
		{
			if (SpaceOxygenProofUtils.INSTANCE.tryProvideProof(living))
			{
				cir.setReturnValue(true);
			}

		}

	}

}
