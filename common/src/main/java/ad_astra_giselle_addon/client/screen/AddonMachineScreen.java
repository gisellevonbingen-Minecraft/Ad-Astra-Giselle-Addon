package ad_astra_giselle_addon.client.screen;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.client.mixin.ad_astra.ConfigurationWidgetAccessor;
import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.IWorkingAreaBlockEntity;
import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.network.WorkingAreaVisibleMessage;
import earth.terrarium.adastra.client.components.PressableImageButton;
import earth.terrarium.adastra.client.components.machines.FluidBarWidget;
import earth.terrarium.adastra.client.components.machines.OptionsBarWidget.Builder;
import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.blockentities.base.ContainerMachineBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.AABB;

public abstract class AddonMachineScreen<MENU extends MachineMenu<BLOCK_ENTITY>, BLOCK_ENTITY extends ContainerMachineBlockEntity> extends MachineScreen<MENU, BLOCK_ENTITY>
{
	public static final Component WORKINGAREA_TEXT = Component.translatable(ctl("workingarea")).withStyle(ChatFormatting.GRAY);

	public static String ctl(String path)
	{
		return AdAstraGiselleAddon.tl("gui", path);
	}

	public static boolean shouldShowRecipeTooltip()
	{
		return !CompatibleManager.JEI.isLoaded() && !CompatibleManager.REI.isLoaded();
	}

	private boolean cachedWorkingAreaVisible;
	protected PressableImageButton workingAreaVisibleButton;

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
			boolean visible = this.isWorkingAreaVisible();
			this.cachedWorkingAreaVisible = visible;
			this.updateWorkingAreaVisibleButton();
		}

	}

	@Override
	public Builder createOptionsBar()
	{
		Builder builder = super.createOptionsBar();

		if (this.entity instanceof IWorkingAreaBlockEntity blockEntity)
		{
			this.workingAreaVisibleButton = this.createWorkingAreaShowMode();
			builder.addElement(0, this.workingAreaVisibleButton);
		}
		else
		{
			this.workingAreaVisibleButton = null;
		}

		return builder;
	}

	@Nullable
	public PressableImageButton createWorkingAreaShowMode()
	{
		if (this.entity instanceof IWorkingAreaBlockEntity blockEntity)
		{
			return new PressableImageButton(0, 0, 18, 18, 0, 0, 18, blockEntity.isWorkingAreaVisible() ? GuiUtils.SHOW_BUTTON : GuiUtils.HIDE_BUTTON, 18, 54, button ->
			{
				this.setWorkingAreaVisible(!this.isWorkingAreaVisible());
			}, WORKINGAREA_TEXT);
		}
		else
		{
			return null;
		}

	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta)
	{
		super.render(guiGraphics, mouseX, mouseY, delta);
		this.updateWorkingAreaVisibleButton();
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

	public void updateWorkingAreaVisibleButton()
	{
		boolean next = this.isWorkingAreaVisible();

		if (this.cachedWorkingAreaVisible != next)
		{
			this.cachedWorkingAreaVisible = next;
			this.workingAreaVisibleButton.setTexture(next ? GuiUtils.SHOW_BUTTON : GuiUtils.HIDE_BUTTON);
		}

	}

	public int getTextColour()
	{
		return 0x303030;
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

}
