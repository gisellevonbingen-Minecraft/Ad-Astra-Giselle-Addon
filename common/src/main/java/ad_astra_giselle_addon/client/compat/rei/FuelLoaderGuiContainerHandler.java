package ad_astra_giselle_addon.client.compat.rei;

import java.awt.Rectangle;

import ad_astra_giselle_addon.client.screen.FuelLoaderScreen;
import ad_astra_giselle_addon.common.compat.rei.AddonReiCommonPlugin;
import dev.architectury.event.CompoundEventResult;
import earth.terrarium.ad_astra.common.compat.rei.BaseClickArea;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.registry.screen.FocusedStackProvider;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.screens.Screen;

public class FuelLoaderGuiContainerHandler extends BaseClickArea<FuelLoaderScreen> implements FocusedStackProvider
{
	@Override
	public Rectangle getBounds(FuelLoaderScreen screen)
	{
		return screen.getFluidTankBounds();
	}

	@Override
	public Result getSuccess(FuelLoaderScreen screen)
	{
		return categoryWithTooltip(AddonReiCommonPlugin.FUEL_LOADER_CATEGORY, screen.getFluidTankTooltip());
	}

	@Override
	public CompoundEventResult<EntryStack<?>> provide(Screen _screen, Point mouse)
	{
		if (_screen instanceof FuelLoaderScreen screen)
		{
			if (screen.getFluidTankBounds().contains(mouse.getX(), mouse.getY()))
			{
				FluidHolder fluid = screen.getFluid();
				return CompoundEventResult.interruptTrue(EntryStacks.of(fluid.getFluid(), fluid.getFluidAmount()));
			}

		}

		return CompoundEventResult.pass();
	}

}
