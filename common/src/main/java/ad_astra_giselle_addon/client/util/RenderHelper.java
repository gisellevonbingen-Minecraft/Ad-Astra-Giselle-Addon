package ad_astra_giselle_addon.client.util;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.client.GraphicsStatus;
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
	public static final ResourceLocation SPRITE_FLUID_BAR = ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, "textures/gui/sprites/fluid_bar.png");

	public static final ResourceLocation TILE_SURFACE = AdAstraGiselleAddon.rl("block/tile_surface");
	private static TextureAtlasSprite atlass = null;

	public static void renderBox(PoseStack poseStack, MultiBufferSource buffer, AABB bounds, int lineR, int lineG, int lineB, int surfaceR, int surfaceG, int surfaceB)
	{
		PoseStack.Pose last = poseStack.last();

		float startX = (float) bounds.minX + 0.005F;
		float topY = (float) bounds.minY + 0.005F;
		float startZ = (float) bounds.minZ + 0.005F;

		float endX = (float) bounds.maxX - 0.005F;
		float botY = (float) bounds.maxY - 0.005F;
		float endZ = (float) bounds.maxZ - 0.005F;
		VertexConsumer builder = buffer.getBuffer(RenderType.lines());

		// Bottom frame
		drawShapeOutline(builder, last, startX, botY, startZ, endX, botY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, last, startX, botY, endZ, endX, botY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, last, startX, botY, startZ, startX, botY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, last, endX, botY, startZ, endX, botY, endZ, lineR, lineG, lineB);

		// Top frame
		drawShapeOutline(builder, last, startX, topY, startZ, endX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, last, startX, topY, endZ, endX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, last, startX, topY, startZ, startX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, last, endX, topY, startZ, endX, topY, endZ, lineR, lineG, lineB);

		// Vertical lines
		drawShapeOutline(builder, last, startX, botY, startZ, startX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, last, startX, botY, endZ, startX, topY, endZ, lineR, lineG, lineB);
		drawShapeOutline(builder, last, endX, botY, startZ, endX, topY, startZ, lineR, lineG, lineB);
		drawShapeOutline(builder, last, endX, botY, endZ, endX, topY, endZ, lineR, lineG, lineB);

		drawSurfaces(buffer, last, startX, startZ, endX, endZ, botY, topY, surfaceR, surfaceG, surfaceB);
	}

	private static void drawShapeOutline(VertexConsumer builder, PoseStack.Pose pose, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b)
	{
		float nX = x2 - x1;
		float nY = y2 - y1;
		float nZ = z2 - z1;
		float sqrt = (float) Math.sqrt(nX * nX + nY * nY + nZ * nZ);
		nX = nX / sqrt;
		nY = nY / sqrt;
		nZ = nZ / sqrt;

		Matrix4f matrix = pose.pose();
		builder.addVertex(matrix, x1, y1, z1).setColor(r, g, b, 0xFF).setNormal(pose, nX, nY, nZ);
		builder.addVertex(matrix, x2, y2, z2).setColor(r, g, b, 0xFF).setNormal(pose, nX, nY, nZ);
	}

	private static void drawSurfaces(MultiBufferSource buffer, PoseStack.Pose pose, float startX, float startZ, float endX, float endZ, float botY, float topY, int r, int g, int b)
	{
		VertexConsumer builder;
		Minecraft minecraft = Minecraft.getInstance();
		GraphicsStatus graphicsFanciness = minecraft.options.graphicsMode().get();

		if (graphicsFanciness == GraphicsStatus.FABULOUS)
		{
			builder = buffer.getBuffer(RenderType.translucentMovingBlock());
		}
		else
		{
			builder = buffer.getBuffer(RenderType.translucent());
		}

		if (atlass == null)
		{
			atlass = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(TILE_SURFACE);
		}

		Matrix4f matrix = pose.pose();
		float maxU = atlass.getU1();
		float minU = atlass.getU0();
		float maxV = atlass.getV1();
		float minV = atlass.getV0();
		int light = 240;
		int alpha = 0x20;

		// Down
		builder.addVertex(matrix, startX, botY, startZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, -1, 0);
		builder.addVertex(matrix, endX, botY, startZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, -1, 0);
		builder.addVertex(matrix, endX, botY, endZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, -1, 0);
		builder.addVertex(matrix, startX, botY, endZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, -1, 0);

		// Top
		builder.addVertex(matrix, endX, topY, startZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
		builder.addVertex(matrix, startX, topY, startZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
		builder.addVertex(matrix, startX, topY, endZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
		builder.addVertex(matrix, endX, topY, endZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);

		// North
		builder.addVertex(matrix, startX, botY, startZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, -1);
		builder.addVertex(matrix, startX, topY, startZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, -1);
		builder.addVertex(matrix, endX, topY, startZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, -1);
		builder.addVertex(matrix, endX, botY, startZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, -1);

		// South
		builder.addVertex(matrix, endX, botY, endZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, 1);
		builder.addVertex(matrix, endX, topY, endZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, 1);
		builder.addVertex(matrix, startX, topY, endZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, 1);
		builder.addVertex(matrix, startX, botY, endZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, 1);

		// West
		builder.addVertex(matrix, startX, botY, endZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, -1, 0, 0);
		builder.addVertex(matrix, startX, topY, endZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, -1, 0, 0);
		builder.addVertex(matrix, startX, topY, startZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, -1, 0, 0);
		builder.addVertex(matrix, startX, botY, startZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, -1, 0, 0);

		// East
		builder.addVertex(matrix, endX, botY, startZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 1, 0, 0);
		builder.addVertex(matrix, endX, topY, startZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 1, 0, 0);
		builder.addVertex(matrix, endX, topY, endZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 1, 0, 0);
		builder.addVertex(matrix, endX, botY, endZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 1, 0, 0);

		// Inside

		// Down
		builder.addVertex(matrix, endX, botY, startZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, -1, 0);
		builder.addVertex(matrix, startX, botY, startZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, -1, 0);
		builder.addVertex(matrix, startX, botY, endZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, -1, 0);
		builder.addVertex(matrix, endX, botY, endZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, -1, 0);

		// Top
		builder.addVertex(matrix, startX, topY, startZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
		builder.addVertex(matrix, endX, topY, startZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
		builder.addVertex(matrix, endX, topY, endZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);
		builder.addVertex(matrix, startX, topY, endZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 1, 0);

		// North
		builder.addVertex(matrix, endX, botY, startZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, -1);
		builder.addVertex(matrix, endX, topY, startZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, -1);
		builder.addVertex(matrix, startX, topY, startZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, -1);
		builder.addVertex(matrix, startX, botY, startZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, -1);

		// South
		builder.addVertex(matrix, startX, botY, endZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, 1);
		builder.addVertex(matrix, startX, topY, endZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, 1);
		builder.addVertex(matrix, endX, topY, endZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, 1);
		builder.addVertex(matrix, endX, botY, endZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 0, 0, 1);

		// West
		builder.addVertex(matrix, endX, botY, endZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, -1, 0, 0);
		builder.addVertex(matrix, endX, topY, endZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, -1, 0, 0);
		builder.addVertex(matrix, endX, topY, startZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, -1, 0, 0);
		builder.addVertex(matrix, endX, botY, startZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, -1, 0, 0);

		// East
		builder.addVertex(matrix, startX, botY, startZ).setColor(r, g, b, alpha).setUv(minU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 1, 0, 0);
		builder.addVertex(matrix, startX, topY, startZ).setColor(r, g, b, alpha).setUv(minU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 1, 0, 0);
		builder.addVertex(matrix, startX, topY, endZ).setColor(r, g, b, alpha).setUv(maxU, maxV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 1, 0, 0);
		builder.addVertex(matrix, startX, botY, endZ).setColor(r, g, b, alpha).setUv(maxU, minV).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(pose, 1, 0, 0);
	}

	private RenderHelper()
	{

	}

}
