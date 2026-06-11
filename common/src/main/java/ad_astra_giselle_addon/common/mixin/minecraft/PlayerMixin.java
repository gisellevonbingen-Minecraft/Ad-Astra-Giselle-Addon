package ad_astra_giselle_addon.common.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ad_astra_giselle_addon.common.config.ItemsConfig;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerMixin extends Entity
{
	public PlayerMixin(EntityType<?> entityType, Level level)
	{
		super(entityType, level);
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void tick(CallbackInfo callbackInfo)
	{
		Player living = (Player) (Object) this;

		if (!living.level().isClientSide() && living.tickCount % ItemsConfig.OXYGEN_CHARGERS.distributionInterval == 0)
		{
			OxygenChargerUtils.distributeToItems(living);
		}

		ProofAbstractUtils.reduceProofDuration(living);
	}

}
