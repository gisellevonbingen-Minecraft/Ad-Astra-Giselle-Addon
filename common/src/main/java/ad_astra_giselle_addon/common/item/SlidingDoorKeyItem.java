package ad_astra_giselle_addon.common.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.SlidingDoorBlockProperties;
import ad_astra_giselle_addon.common.registry.AddonItems;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.ad_astra.common.block.door.SlidingDoorBlock;
import earth.terrarium.ad_astra.common.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SlidingDoorKeyItem extends Item
{
	public static final Component TOOLTIP1 = TranslationUtils.getItemTooltip(AddonItems.SLIDING_DOOR_KEY.getId(), "1");
	public static final Component TOOLTIP2 = TranslationUtils.getItemTooltip(AddonItems.SLIDING_DOOR_KEY.getId(), "2");

	public SlidingDoorKeyItem(Properties pProperties)
	{
		super(pProperties);
	}

	@Nullable
	public static InteractionResult useOnSlidingDoor(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand)
	{
		if (!pLevel.isClientSide && pLevel.getBlockState(pPos).getBlock() instanceof SlidingDoorBlock block)
		{
			BlockPos mainPos = block.getMainPos(pState, pPos);
			BlockState mainState = pLevel.getBlockState(mainPos);

			ItemStack hand = pPlayer.getItemInHand(pHand);
			Item key = AddonItems.SLIDING_DOOR_KEY.get();

			if (hand.is(key) || hand.is(ModTags.WRENCHES))
			{
				mainState = mainState.cycle(SlidingDoorBlockProperties.LOCKED).setValue(SlidingDoorBlockProperties.OPEN, false);
				pLevel.setBlockAndUpdate(mainPos, mainState);
				pPlayer.displayClientMessage(Component.translatable(AdAstraGiselleAddon.tl("info", mainState.getValue(SlidingDoorBlockProperties.LOCKED) ? "locked" : "unlocked")), true);
				return InteractionResult.SUCCESS;
			}
			else if (mainState.getValue(SlidingDoorBlockProperties.LOCKED))
			{
				pPlayer.displayClientMessage(Component.translatable(AdAstraGiselleAddon.tl("info", "sliding_door_locked_1")), false);
				pPlayer.displayClientMessage(Component.translatable(AdAstraGiselleAddon.tl("info", "sliding_door_locked_2"), new ItemStack(key).getHoverName()), false);
				return InteractionResult.SUCCESS;
			}

		}

		return null;
	}

	@Override
	public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced)
	{
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

		pTooltipComponents.add(TOOLTIP1);
		pTooltipComponents.add(TOOLTIP2);
	}

}
