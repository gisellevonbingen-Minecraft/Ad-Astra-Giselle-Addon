package ad_astra_giselle_addon.common.mixin.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.mixin.impl.ad_astra.OxygenApiImplMixinImpl;
import earth.terrarium.adastra.common.systems.OxygenApiImpl;
import net.minecraft.world.entity.Entity;

@Mixin(value = OxygenApiImpl.class, remap = false)
public abstract class OxygenApiImplMixin
{
	@Inject(method = "hasOxygen(Lnet/minecraft/world/entity/Entity;)Z", at = @At("TAIL"), cancellable = true)
	private void hasOxygen(Entity entity, CallbackInfoReturnable<Boolean> cir)
	{
		OxygenApiImplMixinImpl.hasOxygen(entity, cir);
	}

}
