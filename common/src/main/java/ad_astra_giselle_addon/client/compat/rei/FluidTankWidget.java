package ad_astra_giselle_addon.client.compat.rei;

import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import earth.terrarium.ad_astra.client.screens.GuiUtil;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
import net.minecraft.client.gui.components.events.GuiEventListener;

public class FluidTankWidget extends WidgetWithBounds
{
	private final Rectangle bounds;
	private final long capacity;
	private final FluidHolder fluid;
	private final Tooltip tooltip;

	public FluidTankWidget(Point location, long capacity, FluidHolder fluid)
	{
		java.awt.Rectangle awtBounds = GuiUtil.getFluidTankBounds(location.getX(), location.getY());
		this.bounds = new Rectangle(awtBounds.x, awtBounds.y, awtBounds.width, awtBounds.height);
		this.capacity = capacity;
		this.fluid = fluid;
		this.tooltip = Tooltip.create(GuiUtil.getFluidTranslation(fluid.getFluid()));
	}

	@Override
	public @Nullable Tooltip getTooltip(TooltipContext context)
	{
		return this.tooltip;
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float delta)
	{
		Rectangle bounds = this.getBounds();
		GuiUtil.drawFluidTank(stack, bounds.getX(), bounds.getY(), this.getCapacity(), this.getFluid());
	}

	@Override
	public List<? extends GuiEventListener> children()
	{
		return Collections.emptyList();
	}

	@Override
	public Rectangle getBounds()
	{
		return this.bounds;
	}

	public long getCapacity()
	{
		return this.capacity;
	}

	public FluidHolder getFluid()
	{
		return this.fluid;
	}

}
