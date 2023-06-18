package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;

import earth.terrarium.ad_astra.client.screen.GuiUtil;
import net.minecraft.client.gui.GuiGraphics;
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

	public static void drawHorizontal(GuiGraphics guiGraphics, Rectangle bounds, ResourceLocation texture, long amount, long total)
	{
		drawHorizontal(guiGraphics, bounds, texture, getRatio(amount, total));
	}

	public static void drawHorizontal(GuiGraphics guiGraphics, Rectangle bounds, ResourceLocation texture, double ratio)
	{
		GuiUtil.drawHorizontal(guiGraphics, bounds.x, bounds.y, bounds.width, bounds.height, texture, ratio);
	}

	public static void drawVertical(GuiGraphics guiGraphics, Rectangle bounds, ResourceLocation texture, long amount, long total)
	{
		drawVertical(guiGraphics, bounds, texture, getRatio(amount, total));
	}

	public static void drawVertical(GuiGraphics guiGraphics, Rectangle bounds, ResourceLocation texture, double ratio)
	{
		GuiUtil.drawVertical(guiGraphics, bounds.x, bounds.y, bounds.width, bounds.height, texture, ratio);
	}

	public static void drawArrow(GuiGraphics guiGraphics, Rectangle bounds, long amount, long total)
	{
		drawHorizontal(guiGraphics, bounds, GuiUtil.ARROW_TEXTURE, amount, total);
	}

	public static void drawEnergy(GuiGraphics guiGraphics, Rectangle bounds, long amount, long total)
	{
		drawVertical(guiGraphics, bounds, GuiUtil.ENERGY_TEXTURE, amount, total);
	}

}
