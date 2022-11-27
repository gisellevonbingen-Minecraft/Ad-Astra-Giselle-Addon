package ad_astra_giselle_addon.common.mixin.pneumaticcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonPNCUpgrade;
import me.desht.pneumaticcraft.api.item.PNCUpgrade;
import me.desht.pneumaticcraft.common.util.upgrade.ApplicableUpgradesDB;
import net.minecraft.resources.ResourceLocation;

@Mixin(value = ApplicableUpgradesDB.class, remap = false)
public abstract class ApplicableUpgradesDBInject
{
	@Inject(method = "getItemRegistryName", at = @At("HEAD"), cancellable = true)
	private void getItem(PNCUpgrade upgrade, int tier, CallbackInfoReturnable<ResourceLocation> callbackInfo)
	{
		if (upgrade instanceof AddonPNCUpgrade addonUpgrade)
		{
			ResourceLocation name = AddonPNCUpgrade.getItemName(addonUpgrade, tier);
			callbackInfo.setReturnValue(name);
		}

	}

}
