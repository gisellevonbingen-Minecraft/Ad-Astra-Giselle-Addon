package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.content.proof.SpaceFireProofUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

@Mixin(value = SpaceSuitItem.class, remap = false)
public abstract class SpaceSuitItemMixin
{
	@Inject(method = "hasFullSet(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/tags/TagKey;)Z", at = @At("TAIL"), cancellable = true)
	private static void hasFullSet(LivingEntity entity, TagKey<Item> spaceSuitTag, CallbackInfoReturnable<Boolean> cir)
	{
		if (cir.getReturnValueZ())
		{
			return;
		}
		else if (spaceSuitTag == ModItemTags.HEAT_RESISTANT_ARMOR)
		{
			if (SpaceFireProofUtils.INSTANCE.tryProvideProof(entity))
			{
				cir.setReturnValue(true);
			}

		}
		else if (spaceSuitTag == ModItemTags.FREEZE_RESISTANT_ARMOR)
		{
			if (SpaceOxygenProofUtils.INSTANCE.tryProvideProof(entity))
			{
				cir.setReturnValue(true);
			}

		}

	}

}
