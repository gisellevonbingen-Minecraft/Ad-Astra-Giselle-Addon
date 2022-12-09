package ad_astra_giselle_addon.client.screen;

import java.awt.Rectangle;

import com.mojang.blaze3d.vertex.PoseStack;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;
import ad_astra_giselle_addon.common.network.AddonNetwork;
import ad_astra_giselle_addon.common.network.FuelLoaderMessageWorkingAreaVisible;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.ad_astra.client.screens.GuiUtil;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.AABB;

public class FuelLoaderScreen extends AddonMachineScreen<FuelLoaderBlockEntity, FuelLoaderMenu>
{
	public static final ResourceLocation TEXTURE = AdAstraGiselleAddon.rl("textures/gui/container/fuel_loader.png");

	public static final int TANK_LEFT = 67;
	public static final int TANK_TOP = 28;

	private boolean isFluidTankhovering;

	public FuelLoaderScreen(FuelLoaderMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title, TEXTURE);
		this.imageWidth = 176;
		this.imageHeight = 182;
		this.inventoryLabelY = this.imageHeight - 94;
	}

	@Override
	public boolean hasWorkingArea()
	{
		return true;
	}

	@Override
	public boolean isWorkingAreaVisible()
	{
		return this.getMenu().getMachine().isWorkingAreaVisible();
	}

	@Override
	public AABB getWorkingArea()
	{
		return this.getMenu().getMachine().getWorkingArea();
	}

	@Override
	public void setWorkingAreaVisible(boolean visible)
	{
		super.setWorkingAreaVisible(visible);
		AddonNetwork.CHANNEL.sendToServer(new FuelLoaderMessageWorkingAreaVisible(this.getMenu().getMachine(), visible));
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY)
	{
		super.renderBg(stack, partialTicks, mouseX, mouseY);

		FluidHolder fluid = this.getFluid();
		long capacity = this.getCapacity();
		Rectangle bounds = this.getFluidTankBounds();
		GuiUtil.drawFluidTank(stack, this.leftPos + bounds.x, this.topPos + bounds.y, capacity, fluid);
	}

	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float delta)
	{
		super.render(stack, mouseX, mouseY, delta);

		this.isFluidTankhovering = GuiUtil.isHovering(this.getFluidTankBounds(), mouseX - this.leftPos, mouseY - this.topPos);

		if (this.isFluidTankHovering())
		{
			if (!AdAstraGiselleAddon.compats().JEI.isLoaded() && !AdAstraGiselleAddon.compats().REI.isLoaded())
			{
				FluidHolder fluid = this.getFluid();
				long capacity = this.getCapacity();
				GuiUtil.drawTankTooltip(this, stack, fluid, capacity, mouseX, mouseY);
			}

		}

	}

	public Rectangle getFluidTankBounds()
	{
		return GuiUtil.getFluidTankBounds(TANK_LEFT, TANK_TOP);
	}

	public boolean isFluidTankHovering()
	{
		return this.isFluidTankhovering;
	}

	public Component getFluidTankTooltip()
	{
		FluidHolder fluid = this.getFluid();
		long capacity = this.getCapacity();
		return TranslationUtils.fluid(fluid, capacity);
	}

	public FluidHolder getFluid()
	{
		return this.getMenu().getFluids().get(0);
	}

	public long getCapacity()
	{
		return this.getMenu().getMachine().getFluidContainer().getTankCapacity(0);
	}

}