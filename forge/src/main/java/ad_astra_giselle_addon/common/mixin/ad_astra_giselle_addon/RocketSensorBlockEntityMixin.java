package ad_astra_giselle_addon.common.mixin.ad_astra_giselle_addon;

import org.spongepowered.asm.mixin.Mixin;

import ad_astra_giselle_addon.common.block.entity.RocketSensorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

@Mixin(RocketSensorBlockEntity.class)
public abstract class RocketSensorBlockEntityMixin extends BlockEntity
{
	public RocketSensorBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState)
	{
		super(pType, pPos, pBlockState);
	}

	@Override
	public AABB getRenderBoundingBox()
	{
		return INFINITE_EXTENT_AABB;
	}

}
