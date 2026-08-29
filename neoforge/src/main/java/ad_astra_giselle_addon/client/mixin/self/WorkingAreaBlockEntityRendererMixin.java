package ad_astra_giselle_addon.client.mixin.self;

import org.spongepowered.asm.mixin.Mixin;

import ad_astra_giselle_addon.client.renderer.blockentity.WorkingAreaBlockEntityRenderer;
import ad_astra_giselle_addon.common.block.entity.IWorkingAreaBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

@Mixin(value = WorkingAreaBlockEntityRenderer.class, remap = false)
public abstract class WorkingAreaBlockEntityRendererMixin<BE extends BlockEntity & IWorkingAreaBlockEntity> implements BlockEntityRenderer<BE>
{
	@Override
	public AABB getRenderBoundingBox(BE blockEntity)
	{
		return blockEntity.getWorkingArea(blockEntity.getBlockPos());
	}

}
