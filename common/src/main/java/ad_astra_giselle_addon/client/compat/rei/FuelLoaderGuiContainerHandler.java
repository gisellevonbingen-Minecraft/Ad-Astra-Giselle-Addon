package ad_astra_giselle_addon.client.compat.rei;

import java.awt.Rectangle;

import ad_astra_giselle_addon.client.screen.FuelLoaderScreen;
import ad_astra_giselle_addon.client.screen.GuiUtils2;
import ad_astra_giselle_addon.common.compat.rei.AddonReiCommonPlugin;
import dev.architectury.event.CompoundEventResult;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.registry.screen.FocusedStackProvider;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.screens.Screen;

public class FuelLoaderGuiContainerHandler extends AddonClickArea<FuelLoaderScreen> implements FocusedStackProvider
{
	@Override
	public Rectangle getBounds(FuelLoaderScreen screen)
	{
		return GuiUtils2.getBounds(screen.getFluidBarWidget(0));
	}

	@Override
	public Result getSuccess(FuelLoaderScreen screen)
	{
		return category(AddonReiCommonPlugin.FUEL_LOADER_CATEGORY);
	}

	@Override
	public CompoundEventResult<EntryStack<?>> provide(Screen _screen, Point mouse)
	{
		if (_screen instanceof FuelLoaderScreen screen && !screen.canConfigure())
		{
			if (this.getBounds(screen).contains(mouse.getX(), mouse.getY()))
			{
				ResourceStack<FluidResource> fluid = screen.getMenu().getEntity().getFluids(null).getContents(0);
				return CompoundEventResult.interruptTrue(EntryStacks.of(fluid.resource().getType(), fluid.amount()));
			}

		}

		return CompoundEventResult.pass();
	}

}
