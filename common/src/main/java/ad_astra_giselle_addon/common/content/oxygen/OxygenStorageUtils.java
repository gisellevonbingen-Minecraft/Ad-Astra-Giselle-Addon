package ad_astra_giselle_addon.common.content.oxygen;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.compat.create.BacktankOxygenStorage;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import ad_astra_giselle_addon.common.item.OxygenCanItem;
import earth.terrarium.ad_astra.common.registry.ModFluids;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;

public class OxygenStorageUtils
{
	public static OptionalDouble getExtractableStoredRatio(LivingEntity living)
	{
		List<ItemStackReference> items = LivingHelper.getInventoryItems(living);
		long stored = 0L;
		long capacity = 0L;
		int temperature = (int) ModUtils.getWorldTemperature(living.getLevel());

		for (ItemStackReference item : items)
		{
			IOxygenStorage oxygenStorage = OxygenStorageUtils.get(item);

			if (oxygenStorage != null && oxygenStorage.testTemperature(temperature))
			{
				stored += oxygenStorage.getOxygenAmount();
				capacity += oxygenStorage.getOxygenCapacity();
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
	public static IOxygenStorage firstExtractable(LivingEntity living, long extracting)
	{
		return streamExtractable(living, extracting).findFirst().orElse(null);
	}

	@Nullable
	public static Stream<IOxygenStorage> streamExtractable(LivingEntity living, long extracting)
	{
		int temperature = (int) ModUtils.getWorldTemperature(living.getLevel());
		return LivingHelper.getInventoryItems(living).stream().map(OxygenStorageUtils::get).filter(oxygenStorage ->
		{
			if (oxygenStorage != null && oxygenStorage.testTemperature(temperature))
			{
				long extract = oxygenStorage.extractOxygen(living, extracting, true);

				if (extract >= extracting)
				{
					return true;
				}

			}

			return false;
		});
	}

	@Nullable
	public static IOxygenStorage get(ItemStackHolder item)
	{
		if (item.getStack().getItem() instanceof IOxygenStorageItem oxygenStorageItem)
		{
			return oxygenStorageItem.getOxygenStorage(item);
		}

		if (CompatibleManager.Create.isLoaded())
		{
			var storage = BacktankOxygenStorage.getOxygenStroage(item);

			if (storage != null)
			{
				return storage;
			}

		}

		return null;
	}

	public static long insert(LivingEntity living, long amount)
	{
		for (ItemStackReference item : LivingHelper.getSlotItems(living))
		{
			if (amount <= 0)
			{
				break;
			}
			else if (item.getStack().getItem() instanceof OxygenCanItem)
			{
				UniveralFluidHandler tank = UniveralFluidHandler.from(item);
				FluidHolder containedStack = tank.getFluidInTank(0);
				Fluid insertingFluid = ModFluids.OXYGEN.get();

				if (!containedStack.isEmpty())
				{
					insertingFluid = containedStack.getFluid();
				}

				FluidHolder inserting = FluidHooks.newFluidHolder(insertingFluid, amount, null);
				long inserted = tank.insertFluid(inserting, false);
				amount -= inserted;
			}

		}

		return amount;
	}

	private OxygenStorageUtils()
	{

	}

}
