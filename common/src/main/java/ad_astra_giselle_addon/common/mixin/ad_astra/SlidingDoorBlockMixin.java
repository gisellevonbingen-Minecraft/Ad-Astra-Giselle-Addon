package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ad_astra_giselle_addon.common.block.SlidingDoorBlockProperties;
import ad_astra_giselle_addon.common.item.SlidingDoorKeyItem;
import earth.terrarium.ad_astra.common.block.door.SlidingDoorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(SlidingDoorBlock.class)
public abstract class SlidingDoorBlockMixin extends BaseEntityBlock
{
	protected SlidingDoorBlockMixin(Properties pProperties)
	{
		super(pProperties);
	}

	@Shadow(remap = false)
	public abstract BlockPos getMainPos(BlockState state, BlockPos from);

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(Properties properties, CallbackInfo ci)
	{
		this.registerDefaultState(this.defaultBlockState().setValue(SlidingDoorBlockProperties.LOCKED, false));
	}

	@Inject(method = "createBlockStateDefinition", at = @At("TAIL"), cancellable = true)
	private void createBlockStateDefinition(Builder<Block, BlockState> pBuilder, CallbackInfo ci)
	{
		pBuilder.add(SlidingDoorBlockProperties.LOCKED);
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, CallbackInfoReturnable<InteractionResult> cir)
	{
		InteractionResult result = SlidingDoorKeyItem.useOnSlidingDoor(pState, pLevel, pPos, pPlayer, pHand);

		if (result != null)
		{
			cir.setReturnValue(result);
		}

	}

}
