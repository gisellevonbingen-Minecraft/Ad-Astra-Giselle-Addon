package ad_astra_giselle_addon.common.util;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Range;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.content.oxygen.IChargeMode;
import ad_astra_giselle_addon.common.fluid.FluidHelper;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class TranslationUtils
{
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
	public static final int DEFAULT_DIGITS = 1;
	private static final Map<IChargeMode, Component> CHANGE_MODES = new HashMap<>();

	public static final String TEMPERATURE_RANGE = AdAstraGiselleAddon.tl("description", "temperature_range");
	public static final String CHARGE_MODE = AdAstraGiselleAddon.tl("description", "charge_mode");

	static
	{
		NUMBER_FORMAT.setMinimumFractionDigits(DEFAULT_DIGITS);
		NUMBER_FORMAT.setMaximumFractionDigits(DEFAULT_DIGITS);
	}

	public static Component description(String key, Component component)
	{
		MutableComponent value = Component.literal("").withStyle(ChatFormatting.WHITE).append(component);
		return Component.translatable(key).withStyle(ChatFormatting.BLUE).append(": ").append(value);
	}

	public static Component descriptionTemperatureRange(Range<Integer> range)
	{
		return description(TEMPERATURE_RANGE, Component.literal(range.getMinimum() + "℃ ~ " + range.getMaximum() + "℃"));
	}

	public static Component descriptionChargeMode(IChargeMode mode)
	{
		if (mode == null)
		{
			return Component.empty();
		}

		return CHANGE_MODES.computeIfAbsent(mode, k -> description(CHARGE_MODE, k.getDisplayName()));
	}

	public static Component oxygenStorage(long amount, long capacity)
	{
		long amountMB = FluidHooks.toMillibuckets(amount);
		long capacityMB = FluidHooks.toMillibuckets(capacity);
		Style style = Style.EMPTY.withColor(amountMB > 0 ? ChatFormatting.GREEN : ChatFormatting.RED);
		return Component.translatable("tooltip.ad_astra.space_suit", amountMB, capacityMB).setStyle(style);
	}

	public static Component fluid(FluidHolder fluid, long capacity)
	{
		Component name = FluidHelper.getDisplayName(fluid);
		long amountMB = FluidHooks.toMillibuckets(fluid.getFluidAmount());
		long capacityMB = FluidHooks.toMillibuckets(capacity);
		Style style = Style.EMPTY.withColor(ChatFormatting.GOLD);
		return Component.translatable("gauge_text.ad_astra.liquid_storage", amountMB, capacityMB).setStyle(style).append(Component.nullToEmpty(", ")).append(name);
	}

	public static Component formatPercent(double ratio)
	{
		return Component.literal(NUMBER_FORMAT.format(ratio * 100.0D)).append("%");
	}

	private TranslationUtils()
	{

	}

}
