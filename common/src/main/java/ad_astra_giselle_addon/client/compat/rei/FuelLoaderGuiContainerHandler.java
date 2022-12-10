package ad_astra_giselle_addon.client.compat.rei;

import ad_astra_giselle_addon.client.screen.FuelLoaderScreen;
import dev.architectury.event.CompoundEventResult;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.registry.screen.ClickArea;
import me.shedaniel.rei.api.client.registry.screen.FocusedStackProvider;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.screens.Screen;

public class FuelLoaderGuiContainerHandler implements ClickArea<FuelLoaderScreen>, FocusedStackProvider
{
	@Override
	public Result handle(ClickAreaContext<FuelLoaderScreen> context)
	{
		FuelLoaderScreen screen = context.getScreen();

		if (ClickAreaHelper.contains(screen.getFluidTankBounds(), context.getMousePosition()))
		{
			return ClickAreaHelper.categoryWithTooltip(AddonReiPlugin.FUEL_LOADER_CATEGORY, screen.getFluidTankTooltip());
		}
		else
		{
			return Result.fail();
		}

	}

	@Override
	public CompoundEventResult<EntryStack<?>> provide(Screen _screen, Point mouse)
	{
		if (_screen instanceof FuelLoaderScreen screen)
		{
			if (ClickAreaHelper.contains(screen.getFluidTankBounds(), mouse))
			{
				FluidHolder fluid = screen.getFluid();
				return CompoundEventResult.interruptTrue(EntryStacks.of(fluid.getFluid(), fluid.getFluidAmount()));
			}

		}

		return CompoundEventResult.pass();
	}

}
