package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;
import java.text.NumberFormat;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.client.mixin.ad_astra.ConfigurationWidgetAccessor;
import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.IWorkingAreaBlockEntity;
import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.network.WorkingAreaVisibleMessage;
import earth.terrarium.adastra.client.components.machines.FluidBarWidget;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.AABB;

public abstract class AddonMachineScreen<MENU extends MachineMenu<BLOCK_ENTITY>, BLOCK_ENTITY extends ContainerMachineBlockEntity> extends MachineScreen<MENU, BLOCK_ENTITY>
{
	public static final String WORKINGAREA_TEXT_PREFIX = ctl("workingarea.");
	public static final String WORKINGAREA_TEXT_KEY = WORKINGAREA_TEXT_PREFIX + "text";
	public static final ResourceLocation WORKINGAREA_MIDDLE_TEXTURE = AdAstraGiselleAddon.rl("textures/gui/workingarea_middle.png");
	public static final ResourceLocation WORKINGAREA_SIDE_TEXTURE = AdAstraGiselleAddon.rl("textures/gui/workingarea_side.png");

	public static String ctl(String path)
	{
		return AdAstraGiselleAddon.tl("gui", path);
	}

	public static boolean shouldShowRecipeTooltip()
	{
		return !CompatibleManager.JEI.isLoaded() && !CompatibleManager.REI.isLoaded();
	}

	private boolean cachedWorkingAreaVisible;
	private Button workingAreaVisibleButton;

	public AddonMachineScreen(MENU menu, Inventory inventory, Component title, ResourceLocation texture, ResourceLocation slotTexture, int width, int height)
	{
		super(menu, inventory, title, texture, slotTexture, width, height);
	}

	@Override
	protected void init()
	{
		super.init();

		if (this.hasWorkingArea())
		{
			this.workingAreaVisibleButton = this.addRenderableWidget(new Button.Builder(Component.empty(), this::onChangeModeButtonClick).bounds(this.leftPos, this.topPos - 20, 20, 20).build());

			boolean visible = this.isWorkingAreaVisible();
			this.cachedWorkingAreaVisible = visible;
			this.setWorkingAreaVisible(visible);
			this.refreshWorkingAreaVisibleButtonMessage();
		}

	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
	{
		super.render(guiGraphics, mouseX, mouseY, delta);
		this.updateWorkingAreaVisibleButton();
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
		return this.entity instanceof IWorkingAreaBlockEntity;
	}

	public boolean isWorkingAreaVisible()
	{
		return this.entity instanceof IWorkingAreaBlockEntity blockEntity && blockEntity.isWorkingAreaVisible();
	}

	@Nullable
	public AABB getWorkingArea()
	{
		if (this.entity instanceof IWorkingAreaBlockEntity blockEntity)
		{
			return blockEntity.getWorkingArea();
		}
		else
		{
			return null;
		}

	}

	public void setWorkingAreaVisible(boolean visible)
	{
		BLOCK_ENTITY machine = this.entity;

		if (machine instanceof IWorkingAreaBlockEntity blockEntity)
		{
			blockEntity.setWorkingAreaVisible(visible);
			AddonNetwork.CHANNEL.sendToServer(new WorkingAreaVisibleMessage(machine.getBlockPos(), visible));
		}

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
		this.resizeWorkingAreaVisibleButton();
	}

	public Button getWorkingAreaVisibleButton()
	{
		return this.workingAreaVisibleButton;
	}

	protected void drawWorkingAreaText(GuiGraphics guiGraphics, @Nullable AABB workingArea, AbstractWidget left)
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
		int workingAreaLeft = left.getX() + left.getWidth() - this.leftPos;
		int workignAreaTop = -workingAreaHeight;
		int workingAreaOffsetX = workingAreaLeft;
		guiGraphics.blit(WORKINGAREA_SIDE_TEXTURE, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;
		guiGraphics.blit(WORKINGAREA_MIDDLE_TEXTURE, workingAreaOffsetX, workignAreaTop, 0, 0, workingAreaWidth, workingAreaHeight, workingAreaWidth, workingAreaHeight);
		workingAreaOffsetX += workingAreaWidth;
		guiGraphics.blit(WORKINGAREA_SIDE_TEXTURE, workingAreaOffsetX, workignAreaTop, 0, 0, sideWidth, workingAreaHeight, sideWidth, workingAreaHeight);
		workingAreaOffsetX += sideWidth;

		guiGraphics.drawString(this.font, workingAreaText, workingAreaLeft + sideWidth + sidePadding, workignAreaTop + 2, 0x303030);
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

	public int getTextColour()
	{
		return 0x404040;
	}

	public FluidBarWidget getFluidBarWidget(int tank)
	{
		for (GuiEventListener child : this.children())
		{
			if (child instanceof FluidBarWidget widget && child instanceof ConfigurationWidgetAccessor accessor)
			{
				if (accessor.getConfiguration() instanceof FluidConfiguration fluidConfiguration && fluidConfiguration.tank() == tank)
				{
					return widget;
				}

			}

		}

		return null;
	}

	public Rectangle getBounds(AbstractWidget widget)
	{
		return new Rectangle(this.leftPos() + widget.getX(), this.topPos() + widget.getY(), widget.getWidth(), widget.getHeight());
	}

}
