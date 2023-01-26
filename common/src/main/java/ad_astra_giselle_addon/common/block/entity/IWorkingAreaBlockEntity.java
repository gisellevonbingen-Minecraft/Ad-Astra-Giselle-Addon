package ad_astra_giselle_addon.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public interface IWorkingAreaBlockEntity
{
	BlockPos getBlockPos();
	
	BlockState getBlockState();
	
	boolean isWorkingAreaVisible();

	void setWorkingAreaVisible(boolean visible);

	AABB getWorkingArea();

	AABB getWorkingArea(BlockPos pos);
}
