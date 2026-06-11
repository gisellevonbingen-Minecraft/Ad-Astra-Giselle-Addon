package ad_astra_giselle_addon.common.item;

import java.util.Collections;
import java.util.List;

import ad_astra_giselle_addon.common.config.ItemsConfig;
import ad_astra_giselle_addon.common.content.oxygen.ChargeMode;
import ad_astra_giselle_addon.common.content.oxygen.IChargeMode;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenChargerItem;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import ad_astra_giselle_addon.common.registry.AddonDataComponentTypes;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.adastra.common.registry.ModDataManagers;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.common_storage_lib.context.ItemContext;
import earth.terrarium.common_storage_lib.fluid.FluidApi;
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage;
import earth.terrarium.common_storage_lib.fluid.util.FluidProvider;
import earth.terrarium.common_storage_lib.fluid.util.FluidStorageData;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class OxygenCanItem extends Item implements FluidProvider.Item, IOxygenChargerItem, ICreativeTabOutputProvider
{
	public static final String KEY_OXYGEN_CHARGER = "oxygencharger";

	public OxygenCanItem(Properties properties)
	{
		super(properties.stacksTo(1));
	}

	@Override
	public CommonStorage<FluidResource> getFluids(ItemStack stack, ItemContext context)
	{
		return new SimpleFluidStorage(context, ModDataManagers.FLUID_CONTENTS.componentType(), 1, this.getFluidCapacity()).filter(0, FluidPredicates::isOxygen);
	}

	protected long getFluidCapacity()
	{
		return FluidAmounts.toPlatformAmount(ItemsConfig.OXYGEN_CAN.fluidCapacity);
	}

	protected long getFluidTransfer()
	{
		return FluidAmounts.toPlatformAmount(ItemsConfig.OXYGEN_CAN.fluidTransfer);
	}

	@Override
	public void provideCreativeTabOutput(Output output)
	{
		ItemStack stack = new ItemStack(this);
		stack.set(ModDataManagers.FLUID_CONTENTS.componentType(), new FluidStorageData(Collections.singletonList(FluidResource.of(ModFluids.OXYGEN.get()).toStack(this.getFluidCapacity()))));
		output.accept(stack);
	}

	@Override
	public boolean isFoil(ItemStack item)
	{
		IOxygenCharger oxygenCharger = OxygenChargerUtils.get(StorageSlotContext.ofIsolated(item));

		if (oxygenCharger != null && oxygenCharger.getChargeMode() != ChargeMode.NONE)
		{
			long totalAmount = oxygenCharger.getOxygenAmount();

			if (totalAmount > 0)
			{
				return true;
			}

		}

		return super.isFoil(item);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
	{
		ItemStack item = player.getItemInHand(hand);

		if (level.isClientSide())
		{
			return InteractionResultHolder.pass(item);
		}

		StorageSlotContext slot = StorageSlotContext.ofIsolated(item);
		IOxygenCharger oxygenCharger = OxygenChargerUtils.get(slot);

		if (oxygenCharger != null && !player.isShiftKeyDown())
		{
			IChargeMode chargeMode = oxygenCharger.getChargeMode();
			List<IChargeMode> modes = oxygenCharger.getAvailableChargeModes();
			int nextIndex = (modes.indexOf(chargeMode) + 1) % modes.size();
			IChargeMode nextMode = modes.get(nextIndex);
			oxygenCharger.setChargeMode(nextMode);
			player.sendSystemMessage(TranslationUtils.descriptionChargeMode(nextMode));
		}

		return InteractionResultHolder.pass(slot.getItemStack());
	}

	@Override
	public void appendHoverText(ItemStack item, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag)
	{
		super.appendHoverText(item, context, tooltip, flag);

		IOxygenCharger oxygenCharger = OxygenChargerUtils.get(StorageSlotContext.ofIsolated(item));

		if (oxygenCharger != null)
		{
			tooltip.addAll(TranslationUtils.descriptionCanUse(oxygenCharger.canUseOnCold(), oxygenCharger.canUseOnHot()));
			tooltip.add(TranslationUtils.descriptionChargeMode(oxygenCharger.getChargeMode()));

			CommonStorage<FluidResource> fluidContainer = oxygenCharger.getFluidContainer();

			for (int i = 0; i < fluidContainer.size(); i++)
			{
				ResourceStack<FluidResource> contents = fluidContainer.getContents(i);

				if (this instanceof CreativeOxygenCanItem)
				{
					tooltip.add(TranslationUtils.descriptionCreativeOxygen(contents.isEmpty()));
				}
				else
				{
					tooltip.add(TooltipUtils.getFluidComponent(contents, fluidContainer.getLimit(i, contents.resource())));
				}

			}

		}

	}

	@Override
	public boolean isBarVisible(ItemStack item)
	{
		return true;
	}

	private double getOxygenStoredRatio(ItemStack item)
	{
		IOxygenCharger oxygenCharnger = OxygenChargerUtils.get(StorageSlotContext.ofIsolated(item));
		long amount = oxygenCharnger.getOxygenAmount();
		long capacity = oxygenCharnger.getOxygenCapacity();
		return FluidUtils2.getStoredRatio(amount, capacity);
	}

	@Override
	public int getBarWidth(ItemStack item)
	{
		double ratio = this.getOxygenStoredRatio(item);
		return (int) (ratio * 13);
	}

	@Override
	public int getBarColor(ItemStack item)
	{
		double ratio = this.getOxygenStoredRatio(item);
		return Mth.hsvToRgb((float) (ratio / 3.0F), 1.0F, 1.0F);
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
				return false;
			}

		};

	}

	public abstract class AbstractOxygenCharger implements IOxygenCharger
	{
		private final ItemContext context;

		public AbstractOxygenCharger(ItemContext context)
		{
			this.context = context;
		}

		@Override
		public void setChargeMode(IChargeMode mode)
		{
			this.getContext().set(AddonDataComponentTypes.CHARGE_MODE.get(), mode);
		}

		@Override
		public IChargeMode getChargeMode()
		{
			return this.getContext().getOrDefault(AddonDataComponentTypes.CHARGE_MODE.get(), ChargeMode.NONE);
		}

		@Override
		public long getTransferAmount()
		{
			return getFluidTransfer();
		}

		@Override
		public CommonStorage<FluidResource> getFluidContainer()
		{
			return this.getContext().find(FluidApi.ITEM);
		}

		public final ItemContext getContext()
		{
			return this.context;
		}

	}

}
