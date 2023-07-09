package ad_astra_giselle_addon.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public interface IRangedWorkingAreaBlockEntity extends IWorkingAreaBlockEntity
{
	int getWorkingRange();

	AABB getWorkingArea(double range);

	@Override
	public default AABB getWorkingArea(BlockPos pos)
	{
		return this.getWorkingArea(pos, this.getWorkingRange());
	}

	@Override
	public default AABB getWorkingArea()
	{
		return this.getWorkingArea(this.getWorkingRange());
	}

	public default AABB getWorkingArea(BlockPos pos, double range)
	{
		double half = range / 2.0D;
		return new AABB(pos).inflate(range, half, range).move(0.0D, half, 0.0D);
	}

}
