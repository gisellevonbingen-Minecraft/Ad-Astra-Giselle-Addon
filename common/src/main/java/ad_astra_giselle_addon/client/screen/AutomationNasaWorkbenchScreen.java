package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.AutomationNasaWorkbenchBlockEntity;
import ad_astra_giselle_addon.common.menu.AutomationNasaWorkbenchMenu;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Inventory;

public class AutomationNasaWorkbenchScreen extends AddonMachineScreen<AutomationNasaWorkbenchBlockEntity, AutomationNasaWorkbenchMenu>
{
	public static final int ARROW_LEFT = 79;
	public static final int ARROW_TOP = 56;
	public static final int ENERGY_LEFT = 147;
	public static final int ENERGY_TOP = 50;

	public AutomationNasaWorkbenchScreen(AutomationNasaWorkbenchMenu handler, Inventory inventory, Component title)
	{
		super(handler, inventory, title, AdAstraGiselleAddon.rl("textures/gui/container/automation_nasa_workbench.png"));
		this.imageWidth = 177;
		this.imageHeight = 224;
		this.inventoryLabelY = this.imageHeight - 93;
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float delta, int mouseX, int mouseY)
	{
		super.renderBg(guiGraphics, delta, mouseX, mouseY);

		AutomationNasaWorkbenchMenu menu = this.getMenu();
		int cookTime = menu.getCookTime().get();
		int cookTimeTotal = menu.getCookTimeTotal().get();
		long maxCapacity = menu.getMachine().getEnergyStorage().getMaxCapacity();
		GuiUtil2.drawArrow(guiGraphics, this.getArrowBounds(), cookTime, cookTimeTotal);
		GuiUtil2.drawEnergy(guiGraphics, this.getEnergyBounds(), menu.getEnergyAmount(), maxCapacity);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
	{
		super.render(guiGraphics, mouseX, mouseY, delta);

		if (GuiUtil.isHovering(this.getArrowBounds(), mouseX, mouseY))
		{
			if (shouldShowRecipeTooltip())
			{
				guiGraphics.renderTooltip(this.font, this.getCookTimeTooltip(), mouseX, mouseY);
			}

		}
		else if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY))
		{
			AutomationNasaWorkbenchMenu menu = this.getMenu();
			long maxCapacity = menu.getMachine().getEnergyStorage().getMaxCapacity();
			GuiUtil.drawEnergyTooltip(guiGraphics, menu.getEnergyAmount(), maxCapacity, mouseX, mouseY);
		}

	}

	public MutableComponent getCookTimeTooltip()
	{
		AutomationNasaWorkbenchMenu menu = this.getMenu();
		int cookTime = menu.getCookTime().get();
		int cookTimeTotal = menu.getCookTimeTotal().get();
		MutableComponent translatable = Component.translatable("gauge.ad_astra.cook_time", cookTime, cookTimeTotal);
		return translatable;
	}

	public Rectangle getArrowBounds()
	{
		return new Rectangle(this.leftPos + ARROW_LEFT, this.topPos + ARROW_TOP, GuiUtil.ARROW_WIDTH, GuiUtil.ARROW_HEIGHT);
	}

	public Rectangle getEnergyBounds()
	{
		return GuiUtil.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
	}

	@Override
	public int getTextColour()
	{
		return 0x2C282E;
	}

}
