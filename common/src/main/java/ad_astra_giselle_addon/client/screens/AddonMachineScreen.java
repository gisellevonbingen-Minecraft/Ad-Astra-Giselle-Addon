package ad_astra_giselle_addon.client.screens;

import java.text.NumberFormat;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import earth.terrarium.ad_astra.blocks.machines.entity.AbstractMachineBlockEntity;
import earth.terrarium.ad_astra.client.screens.AbstractMachineScreen;
import earth.terrarium.ad_astra.screen.menu.AbstractMachineMenu;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.AABB;

public abstract class AddonMachineScreen<BLOCK_ENTITY extends AbstractMachineBlockEntity, MENU extends AbstractMachineMenu<BLOCK_ENTITY>> extends AbstractMachineScreen<BLOCK_ENTITY, MENU>
{
	public static final String WORKINGAREA_TEXT_PREFIX = ctl("workingarea.");
	public static final String WORKINGAREA_TEXT_KEY = WORKINGAREA_TEXT_PREFIX + "text";
	public static final ResourceLocation WORKINGAREA_MIDDLE_TEXTURE = AdAstraGiselleAddon.rl("textures/gui/workingarea_middle.png");
	public static final ResourceLocation WORKINGAREA_SIDE_TEXTURE = AdAstraGiselleAddon.rl("textures/gui/workingarea_side.png");

	public static String ctl(String path)
	{
		return AdAstraGiselleAddon.tl("gui", path);
	}

	private boolean cachedWorkingAreaVisible;
	private Button workingAreaVisibleButton;

	public AddonMachineScreen(MENU handler, Inventory inventory, Component title, ResourceLocation texture)
	{
		super(handler, inventory, title, texture);
	}

	@Override
	protected void init()
	{
		super.init();

		if (this.hasWorkingArea())
		{
			this.workingAreaVisibleButton = this.addRenderableWidget(new Button(this.leftPos, this.topPos - 20, 20, 20, Component.empty(), this::onChangeModeButtonClick));

			this.resizeWorkingAreaVisibleButton();
			this.refreshWorkingAreaVisibleButtonMessage();
		}

	}

	public void resizeWorkingAreaVisibleButton()
	{
		Button workingAreaVisibleButton = this.getWorkingAreaVisibleButton();
		int messageWidth = workingAreaVisibleButton.getHeight();
		messageWidth = Math.max(messageWidth, this.font.width(this.getWorkingAreaVisibleText(true)));
		messageWidth = Math.max(messageWidth, this.font.width(this.getWorkingAreaVisibleText(false)));
		workingAreaVisibleButton.setWidth(messageWidth + 8);
	}

	public boolean hasWorkingArea()
	{
		return false;
	}

	public boolean isWorkingAreaVisible()
	{
		return false;
	}

	@Nullable
	public AABB getWorkingArea()
	{
		return null;
	}

	public void setWorkingAreaVisible(boolean visible)
	{
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

}
