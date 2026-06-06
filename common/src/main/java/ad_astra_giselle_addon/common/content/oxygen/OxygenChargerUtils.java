package ad_astra_giselle_addon.common.content.oxygen;

import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import earth.terrarium.adastra.common.items.ZipGunItem;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.ItemFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class OxygenChargerUtils
{
	public static final long LEAST_DISTRIBUTION_AMOUNT = FluidConstants.fromMillibuckets(1L);

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
		FluidContainer fluidContainer = oxygenCharger.getFluidContainer();
		Iterable<ItemStackReference> items = oxygenCharger.getChargeMode().getItems(living);
		long transfer = oxygenCharger.getTransferAmount();

		for (ItemStackReference itemRef : items)
		{
			Item item = itemRef.getStack().getItem();

			if (item instanceof SpaceSuitItem || item instanceof ZipGunItem)
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
