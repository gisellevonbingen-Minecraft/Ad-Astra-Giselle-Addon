package ad_astra_giselle_addon.client.util;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.AABB;

public class RenderHelper
{
	public static final ResourceLocation TILE_SURFACE = AdAstraGiselleAddon.rl("block/tile_surface");
	private static TextureAtlasSprite atlass = null;

	public static void renderBox(PoseStack poseStack, MultiBufferSource buffer, AABB bounds, int lineR, int lineG, int lineB, int surfaceR, int surfaceG, int surfaceB)
	{
		PoseStack.Pose last = poseStack.last();
		Matrix3f normal = last.normal();
		Matrix4f matrix4f = last.pose();

		float startX = (float) bounds.minX + 0.005F;
		float topY = (float) bounds.minY + 0.005F;
		float startZ = (float) bounds.minZ + 0.005F;

		float endX = (float) bounds.maxX - 0.005F;
		float botY = (float) bounds.maxY - 0.005F;
		float endZ = (float) bounds.maxZ - 0.005F;
		VertexConsumer builder = buffer.getBuffer(RenderType.lines());

		// Bottom frame
		drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, endX, botY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, botY, endZ, endX, botY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, startX, botY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, botY, startZ, endX, botY, endZ, lineR, lineG, lineB);

		// Top frame
		drawShapeOutline(builder, matrix4f, normal, startX, topY, startZ, endX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, topY, endZ, endX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, topY, startZ, startX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, topY, startZ, endX, topY, endZ, lineR, lineG, lineB);

		// Vertical lines
		drawShapeOutline(builder, matrix4f, normal, startX, botY, startZ, startX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, startX, botY, endZ, startX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, botY, startZ, endX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, matrix4f, normal, endX, botY, endZ, endX, topY, endZ, lineR, lineG, lineB);

		drawSurfaces(buffer, matrix4f, normal, startX, startZ, endX, endZ, botY, topY, surfaceR, surfaceG, surfaceB);
	}

	private static void drawShapeOutline(VertexConsumer builder, Matrix4f matrix, Matrix3f normal, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b)
	{
		float nX = x2 - x1;
		float nY = y2 - y1;
		float nZ = z2 - z1;
		float sqrt = (float) Math.sqrt(nX * nX + nY * nY + nZ * nZ);
		nX = nX / sqrt;
		nY = nY / sqrt;
		nZ = nZ / sqrt;

		builder.vertex(matrix, x1, y1, z1).color(r, g, b, 0xFF).normal(normal, nX, nY, nZ).endVertex();
		builder.vertex(matrix, x2, y2, z2).color(r, g, b, 0xFF).normal(normal, nX, nY, nZ).endVertex();
	}

	private static void drawSurfaces(MultiBufferSource buffer, Matrix4f matrix, Matrix3f normal, float startX, float startZ, float endX, float endZ, float botY, float topY, int r, int g, int b)
	{
		VertexConsumer builder = buffer.getBuffer(RenderType.translucent());
		Minecraft minecraft = Minecraft.getInstance();

		if (atlass == null)
		{
			atlass = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(TILE_SURFACE);
		}

		float maxU = atlass.getU1();
		float minU = atlass.getU0();
		float maxV = atlass.getV1();
		float minV = atlass.getV0();
		int light = 240;
		int alpha = 0x20;

		// Down
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();

		// Top
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();

		// North
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();

		// South
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();

		// West
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();

		// East
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();

		// Inside

		// Down
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, -1, 0).endVertex();

		// Top
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 1, 0).endVertex();

		// North
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, -1).endVertex();

		// South
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 0, 0, 1).endVertex();

		// West
		builder.vertex(matrix, endX, botY, endZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, endX, topY, endZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, endX, topY, startZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();
		builder.vertex(matrix, endX, botY, startZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, -1, 0, 0).endVertex();

		// East
		builder.vertex(matrix, startX, botY, startZ).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, startX, topY, startZ).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, startX, topY, endZ).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
		builder.vertex(matrix, startX, botY, endZ).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, 1, 0, 0).endVertex();
	}

	private RenderHelper()
	{

	}

}
