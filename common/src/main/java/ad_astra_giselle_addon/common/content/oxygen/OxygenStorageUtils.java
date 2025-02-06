package ad_astra_giselle_addon.common.content.oxygen;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.compat.create.BacktankOxygenStorage;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.item.CreativeOxygenCanItem;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import ad_astra_giselle_addon.common.item.OxygenCanItem;
import earth.terrarium.ad_astra.common.registry.ModFluids;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class OxygenStorageUtils
{
	/**
	 *
	 * @param living
	 * @return 0..1, Double.MAX_VALUE
	 */
	public static OptionalDouble getStoredRatio(LivingEntity living)
	{
		return getStoredRatio(stream(living));
	}

	/**
	 *
	 * @param living
	 * @return 0..1, Double.MAX_VALUE
	 */
	public static OptionalDouble getStoredRatio(Stream<ItemStackHolder> items)
	{
		ItemStackHolder[] array = items.toArray(ItemStackHolder[]::new);

		for (ItemStackHolder stack : array)
		{
			if (isInfinifySource(stack.getStack()))
			{
				return OptionalDouble.of(Double.POSITIVE_INFINITY);
			}

		}

		long stored = 0L;
		long capacity = 0L;

		for (ItemStackHolder item : array)
		{
			if (item.getStack().getItem() instanceof CreativeOxygenCanItem)
			{
				continue;
			}

			IOxygenStorage oxygenStorage = OxygenStorageUtils.get(item);

			if (oxygenStorage != null)
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
	public static Stream<ItemStackHolder> stream(LivingEntity living)
	{
		int temperature = (int) ModUtils.getWorldTemperature(living.getLevel());
		List<ItemStackReference> items = LivingHelper.getInventoryItems(living);

		return Stream.concat(items.stream().filter(item ->
		{
			return isInfinifySource(item.getStack());
		}), items.stream().filter(item ->
		{
			return !(item.getStack().getItem() instanceof CreativeOxygenCanItem);
		}).filter(item ->
		{
			var oxygenStorage = OxygenStorageUtils.get(item);
			return oxygenStorage != null && oxygenStorage.testTemperature(temperature);
		}));
	}

	@Nullable
	public static Stream<IOxygenStorage> streamExtractable(LivingEntity living, long extracting)
	{
		return stream(living).map(OxygenStorageUtils::get).filter(oxygenStorage ->
		{
			long extract = oxygenStorage.extractOxygen(living, extracting, true);
			return extract >= extracting;
		});
	}

	public static boolean isInfinifySource(ItemStack item)
	{
		return item.getItem() instanceof CreativeOxygenCanItem type && !type.getFluidContainer(item).isEmpty();
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
