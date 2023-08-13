package ad_astra_giselle_addon.common.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ad_astra_giselle_addon.common.content.jet_suit.JetSuitHandler;
import net.minecraft.world.entity.player.Player;

@Mixin(value = Player.class, priority = 1001)
public abstract class PlayerMixin
{
	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo ci)
	{
		Player player = ((Player) (Object) this);
		JetSuitHandler.updatePlayer(player);
	}

}
