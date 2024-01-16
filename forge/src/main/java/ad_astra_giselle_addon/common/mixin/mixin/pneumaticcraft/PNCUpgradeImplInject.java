package ad_astra_giselle_addon.common.mixin.mixin.pneumaticcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.compat.pneumaticcraft.AddonPNCUpgrades;
import ad_astra_giselle_addon.common.compat.pneumaticcraft.UpgradeDeferredRegister;
import me.desht.pneumaticcraft.api.upgrade.PNCUpgrade;
import me.desht.pneumaticcraft.common.upgrades.PNCUpgradeImpl;
import net.minecraft.resources.ResourceLocation;

@Mixin(value = PNCUpgradeImpl.class, remap = false)
public abstract class PNCUpgradeImplInject
{
	@Inject(method = "getItemRegistryName", at = @At("HEAD"), cancellable = true)
	private void getItemRegistryName(int tier, CallbackInfoReturnable<ResourceLocation> callbackInfo)
	{
		PNCUpgrade self = (PNCUpgrade) this;

		if (AddonPNCUpgrades.UPGRADES.find(self).isPresent())
		{
			callbackInfo.setReturnValue(UpgradeDeferredRegister.getItemName(self, tier));
		}

	}

}
