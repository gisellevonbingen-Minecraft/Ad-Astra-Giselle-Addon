package ad_astra_giselle_addon.client.compat.rei;

import com.mojang.blaze3d.systems.RenderSystem;

import ad_astra_giselle_addon.client.screen.GuiUtils2;
import earth.terrarium.adastra.client.utils.GuiUtils;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.impl.client.gui.widget.EntryWidget;
import net.minecraft.client.gui.GuiGraphics;

public class FluidTankWidget extends EntryWidget
{
	public FluidTankWidget(Rectangle bounds)
	{
		super(bounds);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
	{
		super.render(guiGraphics, mouseX, mouseY, delta);

		Rectangle bounds = this.getBounds();
		RenderSystem.disableDepthTest();
		GuiUtils2.drawVertical(guiGraphics, bounds.x, bounds.y, bounds.width, bounds.height, GuiUtils.FLUID_BAR, 0, 0, 1.0D);
		RenderSystem.enableDepthTest();
	}

}
