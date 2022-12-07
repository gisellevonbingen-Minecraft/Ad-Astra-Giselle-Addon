package ad_astra_giselle_addon.client.compat.rei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ad_astra_giselle_addon.client.screens.FuelLoaderScreen;
import dev.architectury.event.CompoundEventResult;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.registry.screen.ClickArea;
import me.shedaniel.rei.api.client.registry.screen.FocusedStackProvider;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class FuelLoaderGuiContainerHandler implements ClickArea<FuelLoaderScreen>, FocusedStackProvider
{
	@Override
	public Result handle(ClickAreaContext<FuelLoaderScreen> context)
	{
		FuelLoaderScreen screen = context.getScreen();

		if (screen.isFluidTankHovering())
		{
			Result result = Result.success().category(AddonReiPlugin.FUEL_LOADER_CATEGORY);
			List<Component> list = new ArrayList<>();
			list.add(screen.getFluidTankTooltip());
			list.addAll(Arrays.stream(result.getTooltips()).toList());
			Component[] array = list.toArray(new Component[0]);
			return result.tooltip(() -> array);
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
			if (screen.isFluidTankHovering())
			{
				FluidHolder fluid = screen.getFluid();
				return CompoundEventResult.interruptTrue(EntryStacks.of(fluid.getFluid(), fluid.getFluidAmount()));
			}

		}

		return CompoundEventResult.pass();
	}

}
