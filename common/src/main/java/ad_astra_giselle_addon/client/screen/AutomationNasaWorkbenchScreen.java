package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.AutomationNasaWorkbenchBlockEntity;
import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AutomationNasaWorkbenchScreen extends AddonMachineScreen<AutomationNasaWorkbenchMenu, AutomationNasaWorkbenchBlockEntity>
{
	public static final ResourceLocation TEXTURE = AdAstraGiselleAddon.rl("textures/gui/container/automation_nasa_workbench.png");
	public static final int WIDTH = 177;
	public static final int HEIGHT = 224;

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
		GuiUtils2.drawArrow(guiGraphics, this.getArrowBounds(), cookTime, cookTimeTotal);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
	{
		super.render(guiGraphics, mouseX, mouseY, delta);

		if (GuiUtils2.isHovering(this.getArrowBounds(), mouseX, mouseY))
		{
			if (shouldShowRecipeTooltip())
			{
				guiGraphics.renderTooltip(this.font, this.getCookTimeTooltip(), mouseX, mouseY);
			}

		}

	}

	public MutableComponent getCookTimeTooltip()
	{
		AutomationNasaWorkbenchBlockEntity blockEntity = this.entity;
		int cookTime = blockEntity.cookTime();
		int cookTimeTotal = blockEntity.cookTimeTotal();
		MutableComponent translatable = Component.translatable("gauge.ad_astra.cook_time", cookTime, cookTimeTotal);
		return translatable;
	}

	public Rectangle getArrowBounds()
	{
		return new Rectangle(this.leftPos + 79, this.topPos + 56, GuiUtils2.ARROW_WIDTH, GuiUtils2.ARROW_HEIGHT);
	}

	@Override
	public int getTextColour()
	{
		return 0x2C282E;
	}

}
