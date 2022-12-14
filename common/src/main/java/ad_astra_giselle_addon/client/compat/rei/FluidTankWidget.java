package ad_astra_giselle_addon.client.compat.rei;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import earth.terrarium.ad_astra.client.screen.GuiUtil;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.impl.client.gui.widget.EntryWidget;

public class FluidTankWidget extends EntryWidget
{
	public FluidTankWidget(Rectangle bounds)
	{
		super(bounds);
	}

	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float delta)
	{
		super.render(matrices, mouseX, mouseY, delta);

		Rectangle bounds = this.getBounds();
		RenderSystem.disableDepthTest();
		GuiUtil.drawVertical(matrices, bounds.x, bounds.y, bounds.width, bounds.height, GuiUtil.FLUID_TANK_TEXTURE, 1.0D);
		RenderSystem.enableDepthTest();
	}

}
