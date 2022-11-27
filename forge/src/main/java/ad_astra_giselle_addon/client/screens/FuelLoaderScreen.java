package ad_astra_giselle_addon.client.screens;

import java.awt.Rectangle;
import java.text.NumberFormat;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.menu.FuelLoaderContainerMenu;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.network.FuelLoaderMessageWorkingAreaVisible;
import earth.terrarium.ad_astra.client.screens.AbstractMachineScreen;
import earth.terrarium.ad_astra.client.screens.GuiUtil;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.AABB;

public class FuelLoaderScreen extends AbstractMachineScreen<FuelLoaderBlockEntity, FuelLoaderContainerMenu>
{
	public static final String WORKINGAREA_TEXT_PREFIX = ctl("workingarea.");
	public static final String WORKINGAREA_TEXT_KEY = WORKINGAREA_TEXT_PREFIX + "text";
	public static final ResourceLocation WORKINGAREA_MIDDLE_TEXTURE = AdAstraGiselleAddon.rl("textures/gui/workingarea_middle.png");
	public static final ResourceLocation WORKINGAREA_SIDE_TEXTURE = AdAstraGiselleAddon.rl("textures/gui/workingarea_side.png");
	public static final ResourceLocation TEXTURE = AdAstraGiselleAddon.rl("textures/gui/container/fuel_loader.png");

	public static final int TANK_LEFT = 68;
	public static final int TANK_TOP = 28;

	public static String ctl(String path)
	{
		return AdAstraGiselleAddon.tl("gui", path);
	}

	private boolean cachedWorkingAreaVisible;
	private Button workingAreaVisibleButton;

	public FuelLoaderScreen(FuelLoaderContainerMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title, TEXTURE);
		this.imageWidth = 176;
		this.imageHeight = 182;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	protected void init()
	{
		super.init();

		this.workingAreaVisibleButton = this.addRenderableWidget(new Button(this.leftPos, this.topPos - 20, 20, 20, Component.empty(), this::onChangeModeButtonClick));

		this.resizeWorkingAreaVisibleButton();
		this.refreshWorkingAreaVisibleButtonMessage();
	}

	public boolean isWorkingAreaVisible()
	{
		return this.getMenu().getMachine().isWorkingAreaVisible();
	}

	public AABB getWorkingArea()
	{
		return this.getMenu().getMachine().getWorkingArea();
	}

	public void setWorkingAreaVisible(boolean visible)
	{
		AddonNetwork.sendToServer(new FuelLoaderMessageWorkingAreaVisible(this.getMenu().getMachine(), visible));
		this.cachedWorkingAreaVisible = visible;
	}

	public void onChangeModeButtonClick(net.minecraft.client.gui.components.Button button)
	{
		this.setWorkingAreaVisible(!this.isWorkingAreaVisible());
	}

	public void updateWorkingAreaVisibleButton()
	{
		boolean next = this.isWorkingAreaVisible();

		if (this.cachedWorkingAreaVisible != next)
		{
			this.cachedWorkingAreaVisible = next;
			this.refreshWorkingAreaVisibleButtonMessage();
		}

	}

	public void refreshWorkingAreaVisibleButtonMessage()
	{
		Component message = this.getWorkingAreaVisibleText(this.cachedWorkingAreaVisible);
		this.getWorkingAreaVisibleButton().setMessage(message);
	}

	public void resizeWorkingAreaVisibleButton()
	{
		Button workingAreaVisibleButton = this.getWorkingAreaVisibleButton();
		int messageWidth = workingAreaVisibleButton.getHeight();
		messageWidth = Math.max(messageWidth, this.font.width(this.getWorkingAreaVisibleText(true)));
		messageWidth = Math.max(messageWidth, this.font.width(this.getWorkingAreaVisibleText(false)));
		workingAreaVisibleButton.setWidth(messageWidth + 8);
	}

	public Button getWorkingAreaVisibleButton()
	{
		return this.workingAreaVisibleButton;
	}

	protected void drawWorkingAreaText(PoseStack stack, @Nullable AABB workingArea, AbstractWidget left)
	{
		if (workingArea == null)
		{
			return;
		}

		Component workingAreaText = this.getWorkingAreaBoundsText(workingArea);

		int sideWidth = 1;
		int sidePadding = 2;
		int workingAreaWidth = this.font.width(workingAreaText) + (sidePadding * 2);
		int workingAreaHeight = 11;
		int workingAreaLeft = left.x + left.getWidth() - this.leftPos;
		int workignAreaTop = -workingAreaHeight;
		int workingAreaOffsetX = workingAreaLeft;
		RenderSystem.setShaderTexture(0, WORKINGAREA_SIDE_TEXTURE);
		GuiComponent.blit(stack, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;
		RenderSystem.setShaderTexture(0, WORKINGAREA_MIDDLE_TEXTURE);
		GuiComponent.blit(stack, workingAreaOffsetX, workignAreaTop, 0, 0, workingAreaWidth, workingAreaHeight, workingAreaWidth, workingAreaHeight);
		workingAreaOffsetX += workingAreaWidth;
		RenderSystem.setShaderTexture(0, WORKINGAREA_SIDE_TEXTURE);
		GuiComponent.blit(stack, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;

		this.font.draw(stack, workingAreaText, workingAreaLeft + sideWidth + sidePadding, workignAreaTop + 2, 0x303030);
	}

	protected Component getWorkingAreaBoundsText(AABB workingArea)
	{
		NumberFormat numberInstance = NumberFormat.getNumberInstance();
		numberInstance.setMaximumFractionDigits(2);
		Component workingAreaText = Component.translatable(WORKINGAREA_TEXT_KEY, numberInstance.format(workingArea.getXsize()), numberInstance.format(workingArea.getYsize()), numberInstance.format(workingArea.getZsize()));
		return workingAreaText;
	}

	protected Component getWorkingAreaVisibleText(boolean visible)
	{
		String method = visible ? "hide" : "show";
		return Component.translatable(WORKINGAREA_TEXT_PREFIX + method);
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(stack, partialTicks, mouseX, mouseY);
		int tank = 0;
		FluidHolder fluid = this.getMenu().getFluids().get(tank);
		long capacity = this.getMenu().getMachine().getFluidContainer().getTankCapacity(tank);
		Rectangle bounds = this.getFluidTankBounds();
		GuiUtil.drawFluidTank(stack, this.leftPos + bounds.x, this.topPos + bounds.y, capacity, fluid);
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float delta)
	{
		super.render(stack, mouseX, mouseY, delta);

		if (GuiUtil.isHovering(this.getFluidTankBounds(), mouseX - this.leftPos, mouseY - this.topPos) == true)
		{
			if (CompatibleManager.JEI.isLoaded() == false)
			{
				int tank = 0;
				FluidHolder fluid = this.getMenu().getFluids().get(tank);
				long capacity = this.getMenu().getMachine().getFluidContainer().getTankCapacity(tank);
				GuiUtil.drawTankTooltip(this, stack, fluid, capacity, mouseX, mouseY);
			}

		}

	}

	public Rectangle getFluidTankBounds()
	{
		return GuiUtil.getFluidTankBounds(TANK_LEFT, TANK_TOP);
	}

}
