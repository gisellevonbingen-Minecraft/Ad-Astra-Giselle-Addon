package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;

import ad_astra_giselle_addon.common.block.entity.IWorkingAreaBlockEntity;
import earth.terrarium.adastra.common.blockentities.base.MachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

@Mixin(MachineBlockEntity.class)
public abstract class MachineBlockEntityMixin extends BlockEntity
{
	public MachineBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState)
	{
		super(pType, pPos, pBlockState);
	}

	@Override
	public AABB getRenderBoundingBox()
	{
		if (this instanceof IWorkingAreaBlockEntity blockEntity && blockEntity.isWorkingAreaVisible())
		{
			return INFINITE_EXTENT_AABB;
		}
		else
		{
			return super.getRenderBoundingBox();
		}

	}

}
