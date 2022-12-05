package ad_astra_giselle_addon.common.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.registries.AddonAttributes;
import ad_astra_giselle_addon.common.registries.DelegateObjectHolder;
import earth.terrarium.ad_astra.registry.ModDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
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
			OxygenChargerUtils.streamExtractable(living, FluidHooks2.MB_1, null).forEach(c ->
			{
				OxygenChargerUtils.distributeToItems(living, c);
			});

		}

		for (DelegateObjectHolder<Attribute> holder : AddonAttributes.ATTRIBUTES.getObjects())
		{
			Attribute attribute = holder.get();
			AttributeInstance instance = living.getAttribute(attribute);
			double baseValue = instance.getBaseValue();

			if (baseValue > 0.0D)
			{
				instance.setBaseValue(attribute.sanitizeValue(baseValue - 1.0D));
			}

		}

	}

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo)
	{
		if (source == ModDamageSource.OXYGEN)
		{
			LivingEntity living = (LivingEntity) (Object) this;

			if (SpaceOxygenProofUtils.INSTANCE.tryProvideProof(living))
			{
				callbackInfo.setReturnValue(false);
			}

		}

	}

}
