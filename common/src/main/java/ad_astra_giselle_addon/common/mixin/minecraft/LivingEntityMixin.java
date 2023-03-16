package ad_astra_giselle_addon.common.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import earth.terrarium.ad_astra.common.registry.ModDamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity
{
	public LivingEntityMixin(EntityType<?> entityType, Level level)
	{
		super(entityType, level);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo callbackInfo)
	{
		LivingEntity living = (LivingEntity) (Object) this;

		if (!living.getLevel().isClientSide())
		{
			OxygenChargerUtils.distributeToItems(living);
		}

		ProofAbstractUtils.reduceProofDuration(living);
	}

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo)
	{
		if (source.type() == ModDamageSources.of(this.getLevel(), ModDamageSources.OXYGEN).type())
		{
			LivingEntity living = (LivingEntity) (Object) this;

			if (SpaceOxygenProofUtils.INSTANCE.tryProvideProof(living))
			{
				callbackInfo.setReturnValue(false);
			}

		}

	}
}
