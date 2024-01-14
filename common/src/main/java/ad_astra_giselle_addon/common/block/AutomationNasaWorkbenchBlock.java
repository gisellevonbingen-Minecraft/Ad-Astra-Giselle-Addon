package ad_astra_giselle_addon.common.block;

import java.util.List;

import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AutomationNasaWorkbenchBlock extends MachineBlock
{
	public static final Component TOOLTIP = TranslationUtils.styleItemTooltip(ConstantComponents.NASA_WORKBENCH_INFO);

	public AutomationNasaWorkbenchBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public BlockEntityType<?> entity(BlockState state)
	{
		return AddonBlockEntityTypes.AUTOMATION_NASA_WORKBENCH.get();
	}

	@Override
	public void appendHoverText(ItemStack item, BlockGetter level, List<Component> tooltip, TooltipFlag flag)
	{
		super.appendHoverText(item, level, tooltip, flag);
		TooltipUtils.addDescriptionComponent(tooltip, TOOLTIP);
	}

}
