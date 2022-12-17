package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;

import com.mojang.blaze3d.vertex.PoseStack;

import earth.terrarium.ad_astra.client.screen.GuiUtil;
import net.minecraft.resources.ResourceLocation;

public class GuiUtil2
{
	public static double getRatio(long amount, long total)
	{
		return total > 0 ? (amount / (double) total) : 0.0D;
	}

	public static Rectangle getArrowBounds(int x, int y)
	{
		return new Rectangle(x, y, GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT);
	}

	public static void drawHorizontal(PoseStack poseStack, Rectangle bounds, ResourceLocation texture, long amount, long total)
	{
		drawHorizontal(poseStack, bounds, texture, getRatio(amount, total));
	}

	public static void drawHorizontal(PoseStack poseStack, Rectangle bounds, ResourceLocation texture, double ratio)
	{
		GuiUtil.drawHorizontal(poseStack, bounds.x, bounds.y, bounds.width, bounds.height, texture, ratio);
	}

	public static void drawVertical(PoseStack poseStack, Rectangle bounds, ResourceLocation texture, long amount, long total)
	{
		drawVertical(poseStack, bounds, texture, getRatio(amount, total));
	}

	public static void drawVertical(PoseStack poseStack, Rectangle bounds, ResourceLocation texture, double ratio)
	{
		GuiUtil.drawVertical(poseStack, bounds.x, bounds.y, bounds.width, bounds.height, texture, ratio);
	}

	public static void drawArrow(PoseStack poseStack, Rectangle bounds, long amount, long total)
	{
		drawHorizontal(poseStack, bounds, GuiUtil.ARROW_TEXTURE, amount, total);
	}

	public static void drawEnergy(PoseStack poseStack, Rectangle bounds, long amount, long total)
	{
		drawVertical(poseStack, bounds, GuiUtil.ENERGY_TEXTURE, amount, total);
	}

}
