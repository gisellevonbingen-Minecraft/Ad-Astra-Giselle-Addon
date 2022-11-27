package ad_astra_giselle_addon.common.block;

import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import earth.terrarium.ad_astra.blocks.machines.AbstractMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FuelLoaderBlock extends AbstractMachineBlock
{
	public FuelLoaderBlock(BlockBehaviour.Properties properties)
	{
		super(properties.strength(3.0F));
	}

	@Override
	public FuelLoaderBlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new FuelLoaderBlockEntity(pos, state);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state)
	{
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
	{
		if (level.getBlockEntity(pos) instanceof FuelLoaderBlockEntity blockEntity)
		{
			return blockEntity.isWorked() ? 15 : 0;
		}
		else
		{
			return super.getAnalogOutputSignal(state, level, pos);
		}

	}

}
