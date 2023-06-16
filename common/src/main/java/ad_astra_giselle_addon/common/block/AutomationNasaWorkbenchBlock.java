package ad_astra_giselle_addon.common.block;

import java.util.List;

import ad_astra_giselle_addon.common.block.entity.AutomationNasaWorkbenchBlockEntity;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.AbstractMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AutomationNasaWorkbenchBlock extends AbstractMachineBlock
{
	public static final Component TOOLTIP = TranslationUtils.getItemTooltip(new ResourceLocation(AdAstra.MOD_ID, "nasa_workbench"));

	public AutomationNasaWorkbenchBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack item, BlockGetter level, List<Component> tooltip, TooltipFlag flag)
	{
		super.appendHoverText(item, level, tooltip, flag);
		tooltip.add(TOOLTIP);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new AutomationNasaWorkbenchBlockEntity(pos, state);
	}

}
