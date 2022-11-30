package ad_astra_giselle_addon.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.client.util.RenderHelper;
import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class FuelLoaderRenderer implements BlockEntityRenderer<FuelLoaderBlockEntity>
{
	public FuelLoaderRenderer(BlockEntityRendererProvider.Context context)
	{

	}

	@Override
	public boolean shouldRenderOffScreen(FuelLoaderBlockEntity blockEntity)
	{
		return true;
	}

	@Override
	public void render(FuelLoaderBlockEntity blockEntity, float partialTick, PoseStack stack, MultiBufferSource renderer, int light, int overlayLight)
	{
		if (blockEntity.isWorkingAreaVisible())
		{
			int rl = 78;
			int gl = 197;
			int bl = 231;

			int r = 41;
			int g = 148;
			int b = 204;
			int range = blockEntity.getWorkingRange();

			AABB workingArea = blockEntity.getWorkingArea(BlockPos.ZERO, range);
			RenderHelper.renderBox(stack, renderer, workingArea, rl, gl, bl, r, g, b);
		}

	}

}
