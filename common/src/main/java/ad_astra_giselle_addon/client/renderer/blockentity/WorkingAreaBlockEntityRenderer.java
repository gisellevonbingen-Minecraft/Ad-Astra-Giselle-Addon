package ad_astra_giselle_addon.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.client.util.RenderHelper;
import ad_astra_giselle_addon.common.block.entity.IWorkingAreaBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

public class WorkingAreaBlockEntityRenderer<BE extends BlockEntity & IWorkingAreaBlockEntity> implements BlockEntityRenderer<BE>
{
	public WorkingAreaBlockEntityRenderer(BlockEntityRendererProvider.Context context)
	{

	}

	@Override
	public boolean shouldRenderOffScreen(BE blockEntity)
	{
		return true;
	}

	@Override
	public void render(BE blockEntity, float partialTick, PoseStack stack, MultiBufferSource renderer, int light, int overlayLight)
	{
		WorkingAreaBlockEntityRenderer.renderWorkingArea(blockEntity, stack, renderer);
	}

	public static void renderWorkingArea(IWorkingAreaBlockEntity blockEntity, PoseStack stack, MultiBufferSource renderer)
	{
		if (blockEntity.isWorkingAreaVisible())
		{
			int rl = 78;
			int gl = 197;
			int bl = 231;

			int r = 41;
			int g = 148;
			int b = 204;

			AABB workingArea = blockEntity.getWorkingArea();
			RenderHelper.renderBox(stack, renderer, workingArea, rl, gl, bl, r, g, b);
		}

	}

}
