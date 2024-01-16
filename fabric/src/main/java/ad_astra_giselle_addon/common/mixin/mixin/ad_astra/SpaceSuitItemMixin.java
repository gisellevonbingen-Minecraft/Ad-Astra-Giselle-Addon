package ad_astra_giselle_addon.common.mixin.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.mixin.impl.ad_astra.SpaceSuitItemMixinImpl;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

@Mixin(value = SpaceSuitItem.class)
public abstract class SpaceSuitItemMixin
{
	@Inject(method = "hasFullSet(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/tags/TagKey;)Z", at = @At("TAIL"), cancellable = true)
	private static void hasFullSet(LivingEntity entity, TagKey<Item> spaceSuitTag, CallbackInfoReturnable<Boolean> cir)
	{
		SpaceSuitItemMixinImpl.hasFullSet(entity, spaceSuitTag, cir);
	}

}
