package ad_astra_giselle_addon.common.mixin.impl.ad_astra;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.content.proof.SpaceFireProofUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public abstract class SpaceSuitItemMixinImpl
{
	public static void hasFullSet(LivingEntity entity, TagKey<Item> spaceSuitTag, CallbackInfoReturnable<Boolean> cir)
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
