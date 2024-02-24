package ad_astra_giselle_addon.common.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.content.proof.LivingProofDurationAccessor;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.entity.ICustomDataHolder;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mixin(Entity.class)
public abstract class EntityMixin implements ICustomDataHolder, LivingProofDurationAccessor
{
	@Unique
	private CompoundTag ad_astra_giselle_addon$customData = new CompoundTag();

	@Unique
	private Object2IntMap<String> ad_astra_giselle_addon$proofDurations = new Object2IntLinkedOpenHashMap<>();

	@Override
	@Unique
	public CompoundTag ad_astra_giselle_addon$getCustomData()
	{
		return this.ad_astra_giselle_addon$customData;
	}

	@Override
	@Unique
	public int ad_astra_giselle_addon$getProofDuration(ProofAbstractUtils proof)
	{
		return this.ad_astra_giselle_addon$proofDurations.getInt(proof.getDataKey());
	}

	@Override
	@Unique
	public void ad_astra_giselle_addon$setProofDuration(ProofAbstractUtils proof, int duraiton)
	{
		this.ad_astra_giselle_addon$proofDurations.put(proof.getDataKey(), Math.max(duraiton, 0));
	}

	@Inject(method = "load", at = @At("TAIL"))
	public void load(CompoundTag pCompound, CallbackInfo callbackInfo)
	{
		if (pCompound.contains(AdAstraGiselleAddon.MOD_ID, Tag.TAG_COMPOUND))
		{
			this.ad_astra_giselle_addon$customData = pCompound.getCompound(AdAstraGiselleAddon.MOD_ID);

			if ((Entity) (Object) this instanceof LivingEntity)
			{
				this.ad_astra_giselle_addon$proofDurations.clear();

				CompoundTag proofDurations = this.ad_astra_giselle_addon$customData.getCompound(ProofAbstractUtils.KEY_PROOF_DURATION);

				for (ProofAbstractUtils proof : ProofAbstractUtils.PROOFS)
				{
					String key = proof.getDataKey();
					int duration = proofDurations.getInt(key);
					this.ad_astra_giselle_addon$proofDurations.put(key, duration);
				}

			}

		}

	}

	@Inject(method = "saveWithoutId", at = @At("TAIL"))
	public void saveWithoutId(CompoundTag pCompound, CallbackInfoReturnable<CompoundTag> callbackInfo)
	{
		if ((Entity) (Object) this instanceof LivingEntity)
		{
			CompoundTag proofDurations = new CompoundTag();
			this.ad_astra_giselle_addon$customData.put(ProofAbstractUtils.KEY_PROOF_DURATION, proofDurations);

			for (ProofAbstractUtils proof : ProofAbstractUtils.PROOFS)
			{
				String key = proof.getDataKey();
				int duration = this.ad_astra_giselle_addon$proofDurations.getInt(key);
				proofDurations.putInt(key, duration);
			}

		}

		pCompound.put(AdAstraGiselleAddon.MOD_ID, this.ad_astra_giselle_addon$customData);
	}

}
