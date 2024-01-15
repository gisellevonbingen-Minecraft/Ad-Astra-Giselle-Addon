package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.AutomationNasaWorkbenchBlockEntity;
import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AutomationNasaWorkbenchScreen extends AddonMachineScreen<AutomationNasaWorkbenchMenu, AutomationNasaWorkbenchBlockEntity>
{
	public static final ResourceLocation TEXTURE = AdAstraGiselleAddon.rl("textures/gui/container/automation_nasa_workbench.png");
	public static final int WIDTH = 177;
	public static final int HEIGHT = 224;
	public static final int ARROW_LEFT = 79;
	public static final int ARROW_TOP = 56;

	public AutomationNasaWorkbenchScreen(AutomationNasaWorkbenchMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title, TEXTURE, STEEL_SLOT, WIDTH, HEIGHT);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY)
	{
		super.renderBg(guiGraphics, delta, mouseX, mouseY);

		AutomationNasaWorkbenchBlockEntity blockEntity = this.entity;
		int cookTime = blockEntity.cookTime();
		int cookTimeTotal = blockEntity.cookTimeTotal();
		Rectangle arrowBounds = this.getArrowBounds();
		GuiUtils2.drawArrow(guiGraphics, arrowBounds.x, arrowBounds.y, cookTime, cookTimeTotal);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
	{
		super.render(guiGraphics, mouseX, mouseY, delta);

		if (GuiUtils2.isHovering(this.getArrowBounds(), mouseX, mouseY))
		{
			if (shouldShowRecipeTooltip())
			{
				guiGraphics.renderComponentTooltip(this.font, this.getCookTimeTooltip(), mouseX, mouseY);
			}

		}

	}

	public List<Component> getCookTimeTooltip()
	{
		AutomationNasaWorkbenchBlockEntity blockEntity = this.entity;
		int cookTime = blockEntity.cookTime();
		int cookTimeTotal = blockEntity.cookTimeTotal();

		List<Component> tooltip = new ArrayList<>();
		tooltip.add(TooltipUtils.getProgressComponent(cookTime, cookTimeTotal));
		tooltip.add(TooltipUtils.getEtaComponent(cookTime, cookTimeTotal, false));
		return tooltip;
	}

	public Rectangle getArrowBounds()
	{
		return GuiUtils2.getArrowBounds(this.leftPos + ARROW_LEFT, this.topPos + ARROW_TOP);
	}

}
