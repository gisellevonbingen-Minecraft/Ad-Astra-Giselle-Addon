package ad_astra_giselle_addon.common.content.oxygen;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import earth.terrarium.ad_astra.common.item.armor.SpaceSuit;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class OxygenChargerUtils
{
	public static final long LEAST_DISTRIBUTION_AMOUNT = FluidHooks2.MILLI_BUCKET;

	public static void distributeToItems(LivingEntity living)
	{
		OxygenStorageUtils.streamExtractable(living, LEAST_DISTRIBUTION_AMOUNT).forEach(source ->
		{
			if (source instanceof IOxygenCharger charger)
			{
				distributeToItems(living, charger);
			}

		});

	}

	public static void distributeToItems(LivingEntity living, IOxygenCharger oxygenCharger)
	{
		UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();
		Iterable<ItemStackReference> items = oxygenCharger.getChargeMode().getItems(living);
		long transfer = oxygenCharger.getTransferAmount();

		for (ItemStackReference itemRef : items)
		{
			Item item = itemRef.getStack().getItem();

			if (item instanceof SpaceSuit)
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

	@Nullable
	public static IOxygenCharger get(ItemStackHolder item)
	{
		if (item.getStack().getItem() instanceof IOxygenChargerItem oxygenChargerItem)
		{
			return oxygenChargerItem.getOxygenCharger(item);
		}

		return null;
	}

	private OxygenChargerUtils()
	{

	}

}
