package ad_astra_giselle_addon.common.item;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.fluid.CreativeFluidContainer;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.common_storage_lib.context.ItemContext;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import net.minecraft.world.item.ItemStack;

public class CreativeOxygenCanItem extends OxygenCanItem
{
	public CreativeOxygenCanItem(Properties properties)
	{
		super(properties.fireResistant());
	}

	@Override
	protected long getFluidCapacity()
	{
		return CreativeFluidContainer.CAPACITY;
	}

	@Override
	protected long getFluidTransfer()
	{
		return CreativeFluidContainer.CAPACITY;
	}

	@Override
	public CommonStorage<FluidResource> getFluids(ItemStack stack, ItemContext context)
	{
		return new CreativeFluidContainer(context, ModDataManagers.FLUID_CONTENTS.componentType(), FluidPredicates::isOxygen);
	}

	@Override
	public IOxygenCharger getOxygenCharger(ItemContext context)
	{
		return new AbstractOxygenCharger(context)
		{
			@Override
			public boolean canUseOnCold()
			{
				return true;
			}

			@Override
			public boolean canUseOnHot()
			{
				return true;
			}

		};

	}

}
