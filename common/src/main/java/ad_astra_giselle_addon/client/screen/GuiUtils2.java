package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;

public class GuiUtils2
{
	public static final ResourceLocation WIDGETS_TEXTURE = AdAstraGiselleAddon.rl("textures/gui/widgets.png");

	public static final int WORKING_AREA_HEIGHT = 11;

	public static final int WORKING_AREA_SIDE_LEFT = 0;
	public static final int WORKING_AREA_SIDE_TOP = 0;
	public static final int WORKING_AREA_SIDE_WIDTH = 1;

	public static final int WORKING_AREA_MIDDLE_LEFT = 0;
	public static final int WORKING_AREA_MIDDLE_TOP = 1;
	public static final int WORKING_AREA_MIDDLE_WIDTH = 1;

	public static final int ARROW_LEFT = 0;
	public static final int ARROW_TOP = 11;
	public static final int ARROW_WIDTH = 24;
	public static final int ARROW_HEIGHT = 17;

	public static Rectangle getBounds(AbstractWidget widget)
	{
		return new Rectangle(widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight());
	}

	public static boolean isHovering(Rectangle bounds, double x, double y)
	{
		return (bounds.getMinX() <= x && x < bounds.getMaxX()) && (bounds.getMinX() <= y && y < bounds.getMaxY());
	}

	public static double getRatio(long amount, long total)
	{
		return total > 0 ? (amount / (double) total) : 0.0D;
	}

	public static Rectangle getArrowBounds(int x, int y)
	{
		return new Rectangle(x, y, ARROW_WIDTH, ARROW_HEIGHT);
	}

	public static void drawHorizontal(GuiGraphics guiGraphics, int x, int y, int width, int height, ResourceLocation texture, int u, int v, long amount, long total)
	{
		drawHorizontal(guiGraphics, x, y, width, height, texture, u, v, getRatio(amount, total));
	}

	public static void drawHorizontal(GuiGraphics guiGraphics, int x, int y, int width, int height, ResourceLocation texture, int u, int v, double ratio)
	{
		int progress = (int) (ratio * width);
		guiGraphics.blit(texture, x, y, u, v, progress, height);
	}

	public static void drawVertical(GuiGraphics guiGraphics, int x, int y, int width, int height, ResourceLocation texture, int u, int v, long amount, long total)
	{
		drawVertical(guiGraphics, x, y, width, height, texture, u, v, getRatio(amount, total));
	}

	public static void drawVertical(GuiGraphics guiGraphics, int x, int y, int width, int height, ResourceLocation texture, int u, int v, double ratio)
	{
		int progress = (int) ((1.0D - ratio) * height);
		guiGraphics.blit(texture, x, y + progress, u, v + progress, width, height - progress, width, height);
	}

	public static void drawWorkingAreaBackground(GuiGraphics guiGraphics, Rectangle bounds)
	{
		int textureHeight = WORKING_AREA_HEIGHT;
		int sideWidth = WORKING_AREA_SIDE_WIDTH;
		int middleTextureWidth = WORKING_AREA_MIDDLE_WIDTH;
		int x = bounds.x;
		int y = bounds.y;
		int height = bounds.height;
		int middleWidth = bounds.width - sideWidth * 2;

		guiGraphics.blit(WIDGETS_TEXTURE, x, y, WORKING_AREA_SIDE_LEFT, WORKING_AREA_SIDE_TOP, sideWidth, height, sideWidth, textureHeight);
		x += sideWidth;
		guiGraphics.blit(WIDGETS_TEXTURE, x, y, WORKING_AREA_MIDDLE_LEFT, WORKING_AREA_MIDDLE_TOP, middleWidth, height, middleTextureWidth, textureHeight);
		x += middleWidth;
		guiGraphics.blit(WIDGETS_TEXTURE, x, y, WORKING_AREA_SIDE_LEFT, WORKING_AREA_SIDE_TOP, sideWidth, height, sideWidth, textureHeight);
	}

	public static void drawArrow(GuiGraphics guiGraphics, int x, int y, long amount, long total)
	{
		drawHorizontal(guiGraphics, x, y, ARROW_WIDTH, ARROW_HEIGHT, WIDGETS_TEXTURE, ARROW_LEFT, ARROW_TOP, amount, total);
	}

}
