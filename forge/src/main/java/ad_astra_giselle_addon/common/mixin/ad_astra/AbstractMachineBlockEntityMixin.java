package ad_astra_giselle_addon.common.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;

import ad_astra_giselle_addon.common.block.entity.IWorkingAreaBlockEntity;
import earth.terrarium.ad_astra.common.block.machine.entity.AbstractMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

@Mixin(AbstractMachineBlockEntity.class)
public abstract class AbstractMachineBlockEntityMixin extends BlockEntity
{
	public AbstractMachineBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState)
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
