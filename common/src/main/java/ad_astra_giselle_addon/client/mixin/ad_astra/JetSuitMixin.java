package ad_astra_giselle_addon.client.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ad_astra_giselle_addon.common.content.jet_suit.JetSuitHandler;
import earth.terrarium.ad_astra.common.item.armor.JetSuit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@Mixin(JetSuit.class)
public abstract class JetSuitMixin
{
	@Inject(method = "spawnParticles(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;DDD)V", at = @At("HEAD"), cancellable = true)
	private void spawnParticles(Level level, LivingEntity entity, double pitch, double yOffset, double zOffset, CallbackInfo ci)
	{
		ci.cancel();

		JetSuitHandler.spawnParticle(level, entity, pitch, yOffset, zOffset);
	}

}
