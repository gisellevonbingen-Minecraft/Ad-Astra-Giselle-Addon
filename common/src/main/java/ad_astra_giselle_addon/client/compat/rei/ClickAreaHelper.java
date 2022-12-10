package ad_astra_giselle_addon.client.compat.rei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.registry.screen.ClickArea.Result;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.network.chat.Component;

public class ClickAreaHelper
{
	public static boolean contains(Rectangle screenBounds, Point point)
	{
		return screenBounds.contains(point.getX(), point.getY());
	}

	public static Result categoryWithTooltip(CategoryIdentifier<?> category, Component tooltip)
	{
		return categoryWithTooltip(category, Collections.singletonList(tooltip));
	}

	public static Result categoryWithTooltip(CategoryIdentifier<?> category, List<Component> tooltip)
	{
		return categoryWithTooltip(Collections.singletonList(category), tooltip);
	}

	public static Result categoryWithTooltip(List<CategoryIdentifier<?>> categories, Component tooltip)
	{
		return categoryWithTooltip(categories, Collections.singletonList(tooltip));
	}

	public static Result categoryWithTooltip(List<CategoryIdentifier<?>> categories, List<Component> tooltip)
	{
		Result result = Result.success().categories(categories);
		List<Component> newTooltipBuilder = new ArrayList<>();
		newTooltipBuilder.addAll(tooltip);
		newTooltipBuilder.addAll(Arrays.stream(result.getTooltips()).toList());
		Component[] newTooltip = newTooltipBuilder.toArray(new Component[0]);
		return result.tooltip(() -> newTooltip);
	}

	private ClickAreaHelper()
	{

	}

}
