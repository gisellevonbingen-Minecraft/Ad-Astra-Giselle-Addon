package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;

import earth.terrarium.adastra.client.utils.GuiUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;

public class GuiUtils2
{
	public static final int ARROW_WIDTH = 20;
	public static final int ARROW_HEIGHT = 12;

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

	public static void drawHorizontal(GuiGraphics guiGraphics, Rectangle bounds, ResourceLocation texture, long amount, long total)
	{
		drawHorizontal(guiGraphics, bounds, texture, getRatio(amount, total));
	}

	public static void drawHorizontal(GuiGraphics guiGraphics, Rectangle bounds, ResourceLocation texture, double ratio)
	{
		int progress = (int) (ratio * bounds.width);
		guiGraphics.blit(texture, bounds.x, bounds.y, 0, 0, progress, bounds.height, bounds.width, bounds.height);
	}

	public static void drawVertical(GuiGraphics guiGraphics, Rectangle bounds, ResourceLocation texture, long amount, long total)
	{
		drawVertical(guiGraphics, bounds, texture, getRatio(amount, total));
	}

	public static void drawVertical(GuiGraphics guiGraphics, Rectangle bounds, ResourceLocation texture, double ratio)
	{
		int progress = (int) ((1.0D - ratio) * bounds.height);
		guiGraphics.blit(texture, bounds.x, bounds.y + progress, 0, progress, bounds.width, bounds.height - progress, bounds.width, bounds.height);
	}

	public static void drawArrow(GuiGraphics guiGraphics, Rectangle bounds, long amount, long total)
	{
		drawHorizontal(guiGraphics, bounds, GuiUtils.ARROW, amount, total);
	}

}
