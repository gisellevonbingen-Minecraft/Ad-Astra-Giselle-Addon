package ad_astra_giselle_addon.common.block;

import java.util.List;

import ad_astra_giselle_addon.common.block.entity.RocketSensorBlockEntity;
import ad_astra_giselle_addon.common.registry.AddonBlocks;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.ad_astra.common.block.machine.AbstractMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RocketSensorBlock extends AbstractMachineBlock
{
	public static final Component TOOLTIP = TranslationUtils.getItemTooltip(AddonBlocks.ROCKET_SENSOR.getId());

	public RocketSensorBlock(Properties properties)
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
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
	{
		if (level.getBlockEntity(pos) instanceof RocketSensorBlockEntity rocketSensor)
		{
			return rocketSensor.getAnalogSignal();
		}

		return super.getAnalogOutputSignal(state, level, pos);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new RocketSensorBlockEntity(pos, state);
	}

}
