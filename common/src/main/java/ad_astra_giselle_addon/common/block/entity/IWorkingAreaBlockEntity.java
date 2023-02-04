package ad_astra_giselle_addon.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public interface IWorkingAreaBlockEntity
{
	boolean isWorkingAreaVisible();

	void setWorkingAreaVisible(boolean visible);

	AABB getWorkingArea();

	AABB getWorkingArea(BlockPos pos);
}
