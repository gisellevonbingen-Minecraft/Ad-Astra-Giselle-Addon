package ad_astra_giselle_addon.client.compat.jei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.common.platform.IPlatformScreenHelper;
import mezz.jei.common.platform.Services;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;

public class GuiClickableAreaHelper
{
	public static final Component SHOW_RECIPES = Component.translatable("jei.tooltip.show.recipes");

	public static Rect2i getInGuiBounds(AbstractContainerScreen<?> screen, Rectangle rect, double mouseX, double mouseY)
	{
		IPlatformScreenHelper screenHelper = Services.PLATFORM.getScreenHelper();
		int guiLeft = screenHelper.getGuiLeft(screen);
		int guiTop = screenHelper.getGuiTop(screen);
		return new Rect2i(rect.x - guiLeft, rect.y - guiTop, rect.width, rect.height);
	}

	public static List<Component> getShowRecipesTooltip(Component tooltip)
	{
		return getShowRecipesTooltip(Collections.singletonList(tooltip));
	}

	public static List<Component> getShowRecipesTooltip(List<Component> tooltip)
	{
		List<Component> newTooltip = new ArrayList<>();
		newTooltip.addAll(tooltip);
		newTooltip.add(SHOW_RECIPES);
		return newTooltip;
	}

	private GuiClickableAreaHelper()
	{

	}

}
