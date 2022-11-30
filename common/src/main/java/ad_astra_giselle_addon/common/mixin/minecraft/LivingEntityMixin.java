package ad_astra_giselle_addon.common.mixin.minecraft;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import ad_astra_giselle_addon.common.entity.IProofDurationAccesor;
import ad_astra_giselle_addon.common.util.NBTUtils;
import earth.terrarium.ad_astra.registry.ModDamageSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IProofDurationAccesor
{
	private final Map<String, Integer> ad_astra_giselle_addon_proofDurations = new HashMap<>();

	public LivingEntityMixin(EntityType<?> entityType, Level level)
	{
		super(entityType, level);
	}

	@Override
	public int getProofDuration(ProofAbstractUtils proof)
	{
		return this.ad_astra_giselle_addon_proofDurations.getOrDefault(proof.getId().toString(), 0);
	}

	@Override
	public void setPoofDuration(ProofAbstractUtils proof, int duration)
	{
		String key = proof.getId().toString();
		this.setProofDuration(key, duration);
	}

	private void setProofDuration(String key, int duration)
	{
		if (duration <= 0)
		{
			this.ad_astra_giselle_addon_proofDurations.remove(key);
		}
		else
		{
			this.ad_astra_giselle_addon_proofDurations.put(key, duration);
		}

	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void addAdditionalSaveData(CompoundTag tag, CallbackInfo callbackInfo)
	{
		CompoundTag compound = NBTUtils.getOrCreateTag(NBTUtils.getOrCreateTag(tag, AdAstraGiselleAddon.MOD_ID), "proofDurations");

		for (Entry<String, Integer> pair : this.ad_astra_giselle_addon_proofDurations.entrySet())
		{
			compound.putInt(pair.getKey(), pair.getValue());
		}

	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void readAdditionalSaveData(CompoundTag tag, CallbackInfo callbackInfo)
	{
		CompoundTag compound = NBTUtils.getTag(NBTUtils.getTag(tag, AdAstraGiselleAddon.MOD_ID), "proofDurations");

		for (String key : compound.getAllKeys())
		{
			this.ad_astra_giselle_addon_proofDurations.put(key, compound.getInt(key));
		}

	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo callbackInfo)
	{
		LivingEntity living = (LivingEntity) (Object) this;

		if (!living.getLevel().isClientSide())
		{
			OxygenChargerUtils.streamExtractable(living, 1L, null).forEach(c ->
			{
				OxygenChargerUtils.distributeToItems(living, c);
			});

		}

		for (String key : this.ad_astra_giselle_addon_proofDurations.keySet().toArray(new String[0]))
		{
			int duration = this.ad_astra_giselle_addon_proofDurations.get(key);
			this.setProofDuration(key, duration - 1);
		}

	}

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	public void tick(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo)
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
