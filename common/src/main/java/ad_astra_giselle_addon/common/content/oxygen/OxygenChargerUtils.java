package ad_astra_giselle_addon.common.content.oxygen;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import ad_astra_giselle_addon.common.util.LivingHelper;
import earth.terrarium.ad_astra.items.OxygenTankItem;
import earth.terrarium.ad_astra.items.armour.SpaceSuit;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class OxygenChargerUtils
{
	public static void distributeToItems(LivingEntity living, IOxygenCharger oxygenCharger)
	{
		UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();
		Iterable<ItemStackReference> items = oxygenCharger.getChargeMode().getItems(living);
		long transfer = oxygenCharger.getTransferAmount();

		for (ItemStackReference itemRef : items)
		{
			Item item = itemRef.getStack().getItem();

			if (item instanceof SpaceSuit || item instanceof OxygenTankItem)
			{
				UniveralFluidHandler itemFluidHandler = UniveralFluidHandler.fromSafe(itemRef).orElse(null);

				if (itemFluidHandler == null)
				{
					continue;
				}

				FluidHolder moved = FluidHooks2.moveFluidAny(fluidHandler, itemFluidHandler, FluidPredicates::isOxygen, transfer, false);

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

	public static OptionalDouble getInventoryStorageRatio(LivingEntity living)
	{
		List<ItemStackReference> items = LivingHelper.getInventoryStacks(living);
		long stored = 0L;
		long capacity = 0L;

		for (ItemStackReference item : items)
		{
			IOxygenCharger oxygenCharger = OxygenChargerUtils.get(item);

			if (oxygenCharger != null && oxygenCharger.getChargeMode() != ChargeMode.NONE)
			{
				UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();
				stored += FluidHooks2.getStoredAmount(fluidHandler);
				capacity += FluidHooks2.getTotalCapacity(fluidHandler);
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
	public static IOxygenCharger firstExtractable(LivingEntity living, long extracting, @Nullable ItemStack beContains)
	{
		return streamExtractable(living, extracting, beContains).findFirst().orElse(null);
	}

	@Nullable
	public static Stream<IOxygenCharger> streamExtractable(LivingEntity living, long extracting, @Nullable ItemStack beContains)
	{
        int temperature = (int) ModUtils.getWorldTemperature(living.getLevel());
		return LivingHelper.getInventoryStacks(living).stream().map(OxygenChargerUtils::get).filter(oxygenCharger ->
		{
			if (oxygenCharger != null && oxygenCharger.getTemperatureThreshold().contains(temperature))
			{
				FluidHolder extract = FluidHooks2.extractFluid(oxygenCharger.getFluidHandler(), FluidPredicates::isOxygen, extracting, true);

				if (!extract.isEmpty() && extract.getFluidAmount() >= extracting)
				{
					if (beContains == null || oxygenCharger.getChargeMode().getItems(living).stream().anyMatch(ref -> ref.getStack() == beContains))
					{
						return true;
					}

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
