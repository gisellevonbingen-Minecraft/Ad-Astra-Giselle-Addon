package ad_astra_giselle_addon.common.item;

import org.apache.commons.lang3.Range;

import ad_astra_giselle_addon.common.config.ItemsConfig;
import ad_astra_giselle_addon.common.content.oxygen.IChargeMode;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.util.NBTUtils;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.nbt.CompoundTag;

public class NetheriteOxygenCanItem extends OxygenCanItem
{
	public NetheriteOxygenCanItem(Properties properties)
	{
		super(properties.fireResistant());
	}

	@Override
	public long getTankSize()
	{
		return ItemsConfig.NETHERITE_OXYGEN_CAN_FLUID_CAPACITY;
	}

	@Override
	public IOxygenCharger getOxygenCharger(ItemStackHolder item)
	{
		return new IOxygenCharger()
		{
			@Override
			public void setChargeMode(IChargeMode mode)
			{
				CompoundTag tag = NBTUtils.getOrCreateTag(item.getStack(), KEY_OXYGEN_CHARGER);
				tag.put(KEY_CHARGE_MODE, IChargeMode.writeNBT(mode));
			}

			@Override
			public IChargeMode getChargeMode()
			{
				CompoundTag tag = NBTUtils.getTag(item.getStack(), KEY_OXYGEN_CHARGER);
				return IChargeMode.find(this.getAvailableChargeModes(), tag.getString(KEY_CHARGE_MODE));
			}

			@Override
			public long getTransferAmount()
			{
				return ItemsConfig.NETHERITE_OXYGEN_CAN_FLUID_TRANSFER;
			}

			@Override
			public long getFluidCapacity(int tank)
			{
				return getTankSize();
			}

			@Override
			public UniveralFluidHandler getFluidHandler()
			{
				return UniveralFluidHandler.from(item);
			}

			@Override
			public Range<Integer> getTemperatureThreshold()
			{
				return ModItems.NETHERITE_SPACE_SUIT.get().getTemperatureThreshold();
			}
		};

	}

}
