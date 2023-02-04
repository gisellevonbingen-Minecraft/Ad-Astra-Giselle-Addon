package ad_astra_giselle_addon.common.block;

import java.util.List;

import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.ad_astra.common.block.machine.AbstractMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FuelLoaderBlock extends AbstractMachineBlock
{
	public static final Component TOOLTIP = TranslationUtils.getItemTooltip(AddonBlocks.FUEL_LOADER.getId());

	public FuelLoaderBlock(BlockBehaviour.Properties properties)
	{
		super(properties.strength(3.0F));
	}

	@Override
	public void appendHoverText(ItemStack item, BlockGetter level, List<Component> tooltip, TooltipFlag flag)
	{
		super.appendHoverText(item, level, tooltip, flag);
		tooltip.add(TOOLTIP);
	}

	@Override
	public FuelLoaderBlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new FuelLoaderBlockEntity(pos, state);
	}

}
