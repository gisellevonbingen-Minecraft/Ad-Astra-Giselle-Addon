package ad_astra_giselle_addon.common.item;

import java.util.List;

import ad_astra_giselle_addon.common.config.ItemsConfig;
import ad_astra_giselle_addon.common.content.oxygen.ChargeMode;
import ad_astra_giselle_addon.common.content.oxygen.IChargeMode;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenChargerItem;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.util.NBTUtils;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidItem;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class OxygenCanItem extends Item implements BotariumFluidItem<WrappedItemFluidContainer>, IOxygenChargerItem, ICreativeTabOutputProvider
{
	public static final String KEY_OXYGEN_CHARGER = "oxygencharger";
	public static final String KEY_CHARGE_MODE = "chargemode";

	public OxygenCanItem(Properties properties)
	{
		super(properties.stacksTo(1));
	}

	@Override
	public WrappedItemFluidContainer getFluidContainer(ItemStack holder)
	{
		return new WrappedItemFluidContainer(holder, new SimpleFluidContainer(this.getFluidCapacity(), 1, FluidPredicates::isOxygen));
	}

	protected long getFluidCapacity()
	{
		return FluidConstants.fromMillibuckets(ItemsConfig.OXYGEN_CAN_FLUID_CAPACITY);
	}

	protected long getFluidTransfer()
	{
		return FluidConstants.fromMillibuckets(ItemsConfig.OXYGEN_CAN_FLUID_TRANSFER);
	}

	@Override
	public void provideCreativeTabOutput(Output output)
	{
		ItemStackHolder full = new ItemStackHolder(new ItemStack(this));
		IOxygenCharger oxygenCharger = OxygenChargerUtils.get(full);
		oxygenCharger.getFluidContainer().insertFluid(FluidHolder.of(ModFluids.OXYGEN.get(), oxygenCharger.getTotalCapacity(), null), false);
		output.accept(full.getStack());
	}

	@Override
	public Rarity getRarity(ItemStack item)
	{
		return this.isFoil(item) ? Rarity.EPIC : super.getRarity(item);
	}

	@Override
	public boolean isFoil(ItemStack item)
	{
		IOxygenCharger oxygenCharger = OxygenChargerUtils.get(new ItemStackHolder(item));

		if (oxygenCharger != null && oxygenCharger.getChargeMode() != ChargeMode.NONE)
		{
			long totalAmount = oxygenCharger.getTotalAmount();

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

		ItemStackReference holder = new ItemStackReference(item, ItemStackConsumers.hand(hand, player::setItemInHand));
		IOxygenCharger oxygenCharger = OxygenChargerUtils.get(holder);

		if (oxygenCharger != null && !player.isShiftKeyDown())
		{
			IChargeMode chargeMode = oxygenCharger.getChargeMode();
			List<IChargeMode> modes = oxygenCharger.getAvailableChargeModes();
			int nextIndex = (modes.indexOf(chargeMode) + 1) % modes.size();
			IChargeMode nextMode = modes.get(nextIndex);
			oxygenCharger.setChargeMode(nextMode);
			player.sendSystemMessage(TranslationUtils.descriptionChargeMode(nextMode));
		}

		return InteractionResultHolder.pass(item);
	}

	@Override
	public void appendHoverText(ItemStack item, Level level, List<Component> tooltip, TooltipFlag flag)
	{
		super.appendHoverText(item, level, tooltip, flag);

		IOxygenCharger oxygenCharger = OxygenChargerUtils.get(new ItemStackHolder(item));

		if (oxygenCharger != null)
		{
			tooltip.addAll(TranslationUtils.descriptionCanUse(oxygenCharger.canUseOnCold(), oxygenCharger.canUseOnHot()));
			tooltip.add(TranslationUtils.descriptionChargeMode(oxygenCharger.getChargeMode()));

			FluidContainer fluidContainer = oxygenCharger.getFluidContainer();
			List<FluidHolder> fluids = fluidContainer.getFluids();

			for (int i = 0; i < fluids.size(); i++)
			{
				tooltip.add(TooltipUtils.getFluidComponent(fluids.get(i), fluidContainer.getTankCapacity(i)));
			}

		}

	}

	@Override
	public boolean isBarVisible(ItemStack item)
	{
		return true;
	}

	@Override
	public int getBarWidth(ItemStack item)
	{
		double ratio = OxygenChargerUtils.get(new ItemStackHolder(item)).getStoredRatio();
		return (int) (ratio * 13);
	}

	@Override
	public int getBarColor(ItemStack item)
	{
		double ratio = OxygenChargerUtils.get(new ItemStackHolder(item)).getStoredRatio();
		return Mth.hsvToRgb((float) (ratio / 3.0F), 1.0F, 1.0F);
	}

	@Override
	public IOxygenCharger getOxygenCharger(ItemStackHolder item)
	{
		return new AbstractOxygenCharger(item)
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
		private final ItemStackHolder item;

		public AbstractOxygenCharger(ItemStackHolder item)
		{
			this.item = item;
		}

		@Override
		public void setChargeMode(IChargeMode mode)
		{
			CompoundTag tag = NBTUtils.getOrCreateTag(this.getItem().getStack(), KEY_OXYGEN_CHARGER);
			tag.put(KEY_CHARGE_MODE, IChargeMode.writeNBT(mode));
		}

		@Override
		public IChargeMode getChargeMode()
		{
			CompoundTag tag = NBTUtils.getTag(this.getItem().getStack(), KEY_OXYGEN_CHARGER);
			return IChargeMode.readNBT(tag.get(KEY_CHARGE_MODE));
		}

		@Override
		public long getTransferAmount()
		{
			return getFluidTransfer();
		}

		@Override
		public FluidContainer getFluidContainer()
		{
			return FluidContainer.of(this.getItem());
		}

		public final ItemStackHolder getItem()
		{
			return this.item;
		}

	}

}
