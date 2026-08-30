package ad_astra_giselle_addon.common.content.oxygen;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.compat.CompatibleManager;
import ad_astra_giselle_addon.common.compat.create.BacktankOxygenStorage;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.item.CreativeOxygenCanItem;
import ad_astra_giselle_addon.common.item.OxygenCanItem;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

// TODO: Support LivingEntity
public class OxygenStorageUtils
{
	/**
	 *
	 * @param living
	 * @return 0..1, Double.MAX_VALUE
	 */
	public static OptionalDouble getStoredRatio(Player living)
	{
		return getStoredRatio(stream(living));
	}

	/**
	 *
	 * @param living
	 * @return 0..1, Double.MAX_VALUE
	 */
	public static OptionalDouble getStoredRatio(Stream<StorageSlotContext> slots)
	{
		StorageSlotContext[] array = slots.toArray(StorageSlotContext[]::new);

		for (StorageSlotContext slot : array)
		{
			if (isInfinifySource(slot))
			{
				return OptionalDouble.of(Double.POSITIVE_INFINITY);
			}

		}

		long stored = 0L;
		long capacity = 0L;

		for (StorageSlotContext slot : array)
		{
			if (slot.getItem() instanceof CreativeOxygenCanItem)
			{
				continue;
			}

			IOxygenStorage oxygenStorage = OxygenStorageUtils.get(slot);

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
	public static IOxygenStorage firstExtractable(Player living, long extracting)
	{
		return streamExtractable(living, extracting).findFirst().orElse(null);
	}

	@Nullable
	public static Stream<StorageSlotContext> stream(Player living)
	{
		Level level = living.level();
		BlockPos pos = living.blockPosition();
		boolean isCold = TemperatureApi.API.isCold(level, pos);
		boolean isHot = TemperatureApi.API.isHot(level, pos);
		List<StorageSlotContext> slots = LivingHelper.getInventorySlots(living);

		return Stream.concat(slots.stream().filter(slot ->
		{
			return isInfinifySource(slot);
		}), slots.stream().filter(item ->
		{
			return !(item.getItem() instanceof CreativeOxygenCanItem);
		}).filter(slot ->
		{
			var oxygenStorage = OxygenStorageUtils.get(slot);
			return oxygenStorage != null && oxygenStorage.canUse(isCold, isHot);
		}));
	}

	@Nullable
	public static Stream<IOxygenStorage> streamExtractable(Player living, long extracting)
	{
		return stream(living).map(OxygenStorageUtils::get).filter(oxygenStorage ->
		{
			long extract = oxygenStorage.extractOxygen(living, extracting, true);
			return extract >= extracting;
		});
	}

	public static boolean isInfinifySource(StorageSlotContext slot)
	{
		return slot.getItem() instanceof CreativeOxygenCanItem type && !type.getFluids(slot.getItemStack(), slot).getResource(0).isBlank();
	}

	@Nullable
	public static IOxygenStorage get(StorageSlotContext slot)
	{
		IOxygenCharger oxygenCharger = OxygenChargerUtils.get(slot);

		if (oxygenCharger != null)
		{
			return oxygenCharger;
		}

		if (slot.getItem() instanceof IOxygenStorageItem oxygenStorageItem)
		{
			return oxygenStorageItem.getOxygenStorage(slot);
		}

		if (CompatibleManager.Create.isLoaded())
		{
			var storage = BacktankOxygenStorage.getOxygenStroage(slot);

			if (storage != null)
			{
				return storage;
			}

		}

		return null;
	}

	public static long insert(Player living, long amount)
	{
		for (StorageSlotContext slot : LivingHelper.getSlots(living))
		{
			if (amount <= 0)
			{
				break;
			}
			else if (slot.getItem() instanceof OxygenCanItem)
			{
				CommonStorage<FluidResource> tank = slot.find(FluidApi.ITEM);
				ResourceStack<FluidResource> containedStack = tank.size() == 0 ? ResourceStack.EMPTY_FLUID : tank.getContents(0);
				FluidResource insertingFluid = FluidResource.of(ModFluids.OXYGEN.get());

				if (!containedStack.isEmpty())
				{
					insertingFluid = containedStack.resource();
				}

				long inserted = tank.insert(insertingFluid, amount, false);
				amount -= inserted;
			}

		}

		return amount;
	}

	private OxygenStorageUtils()
	{

	}

}
