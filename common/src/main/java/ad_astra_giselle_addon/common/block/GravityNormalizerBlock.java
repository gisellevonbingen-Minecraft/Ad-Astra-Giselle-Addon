package ad_astra_giselle_addon.common.block;

import java.util.List;

import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GravityNormalizerBlock extends MachineBlock
{
	public static final Component TOOLTIP = TranslationUtils.getItemTooltip(AddonBlocks.GRAVITY_NORMALIZER.getId());

	public GravityNormalizerBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public BlockEntityType<?> entity(BlockState state)
	{
		return AddonBlockEntityTypes.GRAVITY_NORMALIZER.get();
	}

	@Override
	public void appendHoverText(ItemStack item, BlockGetter level, List<Component> tooltip, TooltipFlag flag)
	{
		super.appendHoverText(item, level, tooltip, flag);
		tooltip.add(TOOLTIP);
	}

}
