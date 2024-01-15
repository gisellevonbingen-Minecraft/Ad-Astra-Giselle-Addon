package ad_astra_giselle_addon.common.content.oxygen;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.common.items.GasTankItem;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.ItemFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class OxygenChargerUtils
{
	public static void distributeToItems(LivingEntity living)
	{
		streamExtractable(living, FluidConstants.fromMillibuckets(1L)).forEach(c ->
		{
			distributeToItems(living, c);
		});

	}

	public static void distributeToItems(LivingEntity living, IOxygenCharger oxygenCharger)
	{
		FluidContainer fluidContainer = oxygenCharger.getFluidContainer();
		Iterable<ItemStackReference> items = oxygenCharger.getChargeMode().getItems(living);
		long transfer = oxygenCharger.getTransferAmount();

		for (ItemStackReference itemRef : items)
		{
			Item item = itemRef.getStack().getItem();

			if (item instanceof SpaceSuitItem || item instanceof GasTankItem)
			{
				ItemFluidContainer itemFluidContainer = FluidContainer.of(itemRef);

				if (itemFluidContainer == null)
				{
					continue;
				}

				FluidHolder moved = FluidUtils2.moveFluidAny(fluidContainer, itemFluidContainer, FluidPredicates::isOxygen, transfer, false);

				if (!moved.isEmpty())
				{
					transfer -= moved.getFluidAmount();

					if (transfer <= 0)
					{
						break;
					}

				}

			}

		}

	}

	public static OptionalDouble getExtractableStoredRatio(LivingEntity living)
	{
		List<ItemStackReference> items = LivingHelper.getInventoryItems(living);
		long stored = 0L;
		long capacity = 0L;
		Level level = living.level();
		BlockPos pos = living.blockPosition();
		boolean isCold = TemperatureApi.API.isCold(level, pos);
		boolean isHot = TemperatureApi.API.isHot(level, pos);

		for (ItemStackReference item : items)
		{
			IOxygenCharger oxygenCharger = OxygenChargerUtils.get(item);

			if (oxygenCharger != null && oxygenCharger.canUse(isCold, isHot))
			{
				stored += oxygenCharger.getTotalAmount();
				capacity += oxygenCharger.getTotalCapacity();
			}

		}

		if (capacity == 0L)
		{
			return OptionalDouble.empty();
		}
		else
		{
			return OptionalDouble.of((double) stored / capacity);
		}

	}

	@Nullable
	public static IOxygenCharger firstExtractable(LivingEntity living, long extracting)
	{
		return streamExtractable(living, extracting).findFirst().orElse(null);
	}

	@Nullable
	public static Stream<IOxygenCharger> streamExtractable(LivingEntity living, long extracting)
	{
		Level level = living.level();
		BlockPos pos = living.blockPosition();
		boolean isCold = TemperatureApi.API.isCold(level, pos);
		boolean isHot = TemperatureApi.API.isHot(level, pos);

		return LivingHelper.getInventoryItems(living).stream().map(OxygenChargerUtils::get).filter(oxygenCharger ->
		{
			if (oxygenCharger != null && oxygenCharger.canUse(isCold, isHot))
			{
				FluidHolder extract = FluidUtils2.extractFluid(oxygenCharger.getFluidContainer(), FluidPredicates::isOxygen, extracting, true);

				if (!extract.isEmpty() && extract.getFluidAmount() >= extracting)
				{
					return true;
				}

			}

			return false;
		});
	}

	@Nullable
	public static IOxygenCharger get(ItemStackHolder item)
	{
		if (item.getStack().getItem() instanceof IOxygenChargerItem oxygenChargerItem)
		{
			return oxygenChargerItem.getOxygenCharger(item);
		}
		else
		{
			return null;
		}

	}

	private OxygenChargerUtils()
	{

	}

}
