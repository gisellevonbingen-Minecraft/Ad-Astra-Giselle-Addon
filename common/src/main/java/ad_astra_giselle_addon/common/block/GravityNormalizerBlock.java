package ad_astra_giselle_addon.common.block;

import ad_astra_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import earth.terrarium.ad_astra.common.block.machine.AbstractMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GravityNormalizerBlock extends AbstractMachineBlock
{
	public GravityNormalizerBlock(Properties properties)
	{
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new GravityNormalizerBlockEntity(pos, state);
	}

}
