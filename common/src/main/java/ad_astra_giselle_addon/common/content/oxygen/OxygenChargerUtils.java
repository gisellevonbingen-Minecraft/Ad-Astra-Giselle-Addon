package ad_astra_giselle_addon.common.content.oxygen;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import earth.terrarium.adastra.common.items.ZipGunItem;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class OxygenChargerUtils
{
	public static final long LEAST_DISTRIBUTION_AMOUNT = FluidAmounts.toPlatformAmount(1L);

	public static void distributeToItems(Player living)
	{
		OxygenStorageUtils.streamExtractable(living, LEAST_DISTRIBUTION_AMOUNT).forEach(source ->
		{
			if (source instanceof IOxygenCharger charger)
			{
				distributeToItems(living, charger);
			}

		});

	}

	public static void distributeToItems(Player living, IOxygenCharger oxygenCharger)
	{
		CommonStorage<FluidResource> fluidContainer = oxygenCharger.getFluidContainer();
		Iterable<StorageSlotContext> slots = oxygenCharger.getChargeMode().getSlots(living);
		long transfer = oxygenCharger.getTransferAmount();

		for (StorageSlotContext slot : slots)
		{
			Item item = slot.getItem();

			if (item instanceof SpaceSuitItem || item instanceof ZipGunItem)
			{
				CommonStorage<FluidResource> itemFluidContainer = slot.find(FluidApi.ITEM);

				if (itemFluidContainer == null)
				{
					continue;
				}

				ResourceStack<FluidResource> moved = FluidUtils2.moveFluidAny(fluidContainer, itemFluidContainer, FluidPredicates::isOxygen, transfer, false);

				if (!moved.isEmpty())
				{
					transfer -= moved.amount();

					if (transfer <= 0)
					{
						break;
					}

				}

			}

		}

	}

	@Nullable
	public static IOxygenCharger get(StorageSlotContext slot)
	{
		if (slot.getItem() instanceof IOxygenChargerItem oxygenChargerItem)
		{
			return oxygenChargerItem.getOxygenCharger(slot);
		}

		return null;
	}

	private OxygenChargerUtils()
	{

	}

}
