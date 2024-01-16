package ad_astra_giselle_addon.common.mixin.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.mixin.impl.ad_astra.GravityApiImplMixinImpl;
import earth.terrarium.adastra.common.systems.GravityApiImpl;
import net.minecraft.world.entity.Entity;

@Mixin(value = GravityApiImpl.class)
public abstract class GravityApiImplMixin
{
	@Inject(method = "getGravity(Lnet/minecraft/world/entity/Entity;)F", at = @At("TAIL"), cancellable = true)
	private void getGravity(Entity entity, CallbackInfoReturnable<Float> cir)
	{
		GravityApiImplMixinImpl.getGravity(entity, cir);
	}

}
