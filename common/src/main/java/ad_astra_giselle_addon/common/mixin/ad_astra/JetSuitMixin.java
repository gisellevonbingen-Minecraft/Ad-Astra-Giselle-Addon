package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ad_astra_giselle_addon.common.content.jet_suit.IJetSuitAccessor;
import ad_astra_giselle_addon.common.content.jet_suit.JetSuitUtils;
import earth.terrarium.ad_astra.common.item.armor.JetSuit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(JetSuit.class)
public abstract class JetSuitMixin implements IJetSuitAccessor
{
	@Shadow
	private boolean isFallFlying = false;

	@Override
	public boolean ad_astra_giselle_addon$isFallFlying()
	{
		return this.isFallFlying;
	}

	@Inject(method = "fly", at = @At("HEAD"), cancellable = true)
	public void fly(Player player, ItemStack jetSuit, CallbackInfo ci)
	{
		if (!JetSuitUtils.isPowerEnabled(jetSuit) || (JetSuitUtils.isHoverEnabled(jetSuit) && player.isShiftKeyDown()))
		{
			ci.cancel();
			JetSuitUtils.cancelFlying(player, jetSuit);
		}

	}

}
