package ad_astra_giselle_addon.common.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.entity.ICustomDataHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;

@Mixin(Entity.class)
public abstract class EntityMixin implements ICustomDataHolder
{
	@Unique
	private CompoundTag ad_astra_giselle_addon$customData = new CompoundTag();

	@Override
	public CompoundTag ad_astra_giselle_addon$getCustomData()
	{
		return this.ad_astra_giselle_addon$customData;
	}

	@Inject(method = "load", at = @At("TAIL"))
	public void load(CompoundTag pCompound, CallbackInfo callbackInfo)
	{
		if (pCompound.contains(AdAstraGiselleAddon.MOD_ID, Tag.TAG_COMPOUND))
		{
			this.ad_astra_giselle_addon$customData = pCompound.getCompound(AdAstraGiselleAddon.MOD_ID);
		}

	}

	@Inject(method = "saveWithoutId", at = @At("TAIL"))
	public void saveWithoutId(CompoundTag pCompound, CallbackInfoReturnable<CompoundTag> callbackInfo)
	{
		pCompound.put(AdAstraGiselleAddon.MOD_ID, this.ad_astra_giselle_addon$customData);
	}

}
