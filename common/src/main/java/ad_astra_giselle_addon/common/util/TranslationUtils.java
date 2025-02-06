package ad_astra_giselle_addon.common.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.content.oxygen.IChargeMode;
import ad_astra_giselle_addon.common.fluid.FluidHelper;
import ad_astra_giselle_addon.common.registry.ObjectRegistry;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class TranslationUtils
{
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
	public static final int DEFAULT_DIGITS = 1;

	private static final Map<CanUseTuple, List<Component>> CAN_USES = new HashMap<>();
	private static final Map<Boolean, Component> CAN_USE_AVAILABLES = new HashMap<>();
	public static final String CAN_USE = AdAstraGiselleAddon.tl("description", "can_use");
	public static final String CAN_USE_COLD = AdAstraGiselleAddon.tl("description", "can_use.cold");
	public static final String CAN_USE_HOT = AdAstraGiselleAddon.tl("description", "can_use.hot");
	public static final String CAN_USE_AVAILABLE = AdAstraGiselleAddon.tl("can_use", "available");
	public static final String CAN_USE_UNAVAILABLE = AdAstraGiselleAddon.tl("can_use", "unavailable");

	private static final Map<IChargeMode, Component> CHANGE_MODES = new HashMap<>();
	public static final String CHARGE_MODE = AdAstraGiselleAddon.tl("description", "charge_mode");

	private static final Map<Boolean, Component> CREATIVE_OXYGENS = new HashMap<>();
	public static final String CREATIVE_OXYGEN = AdAstraGiselleAddon.tl("description", "creative_oxygen");
	public static final String CREATIVE_OXYGEN_EMPTY = AdAstraGiselleAddon.tl("creative_oxygen", "empty");
	public static final String CREATIVE_OXYGEN_INFINITY = AdAstraGiselleAddon.tl("creative_oxygen", "infinity");

	static
	{
		NUMBER_FORMAT.setMinimumFractionDigits(DEFAULT_DIGITS);
		NUMBER_FORMAT.setMaximumFractionDigits(DEFAULT_DIGITS);
	}

	public static Component getItemTooltip(ResourceLocation id)
	{
		return getItemTooltip(id.getNamespace(), id.getPath(), "");
	}

	public static Component getItemTooltip(ResourceLocation id, String suffix)
	{
		return getItemTooltip(id.getNamespace(), id.getPath(), suffix);
	}

	public static Component getItemTooltip(String namespace, String path, String suffix)
	{
		String tooltipKey = new StringBuilder().append("item.").append(namespace).append(".").append(path).append(".tooltip").append(suffix).toString();
		return styleItemTooltip(Component.translatable(tooltipKey));
	}

	public static Component styleItemTooltip(Component component)
	{
		return Component.empty().append(component).setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE));
	}

	public static Component description(String key, Component component)
	{
		MutableComponent value = Component.literal("").withStyle(ChatFormatting.WHITE).append(component);
		return Component.translatable(key).withStyle(ChatFormatting.BLUE).append(": ").append(value);
	}

	public static List<Component> descriptionCanUse(boolean canUseOnCold, boolean canUseOnHot)
	{
		return CAN_USES.computeIfAbsent(new CanUseTuple(canUseOnCold, canUseOnHot), k ->
		{
			return List.of(descriptionCanUse(CAN_USE_COLD, k.canUseOnCold()), descriptionCanUse(CAN_USE_HOT, k.canUseOnHot()));
		});
	}

	private static Component descriptionCanUse(String key, boolean canUse)
	{
		return CAN_USE_AVAILABLES.computeIfAbsent(canUse, k -> description(key, Component.translatable(k ? CAN_USE_AVAILABLE : CAN_USE_UNAVAILABLE).withStyle(k ? ChatFormatting.GREEN : ChatFormatting.RED)));
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
		long amountMB = FluidConstants.toMillibuckets(amount);
		long capacityMB = FluidConstants.toMillibuckets(capacity);
		Style style = Style.EMPTY.withColor(amountMB > 0 ? ChatFormatting.GREEN : ChatFormatting.RED);
		return Component.translatable("tooltip.ad_astra.space_suit", amountMB, capacityMB).setStyle(style);
	}

	public static Component descriptionCreativeOxygen(boolean empty)
	{
		return CREATIVE_OXYGENS.computeIfAbsent(empty, k ->
		{
			return description(CREATIVE_OXYGEN, Component.translatable(k ? CREATIVE_OXYGEN_EMPTY : CREATIVE_OXYGEN_INFINITY)//
					.withStyle(k ? ChatFormatting.RED : ChatFormatting.GREEN));
		});
	}

	public static List<Component> fluid(FluidContainer container)
	{
		List<Component> list = new ArrayList<>();
		int size = container.getSize();
		List<FluidHolder> fluids = container.getFluids();

		for (int i = 0; i < size; i++)
		{
			FluidHolder fluid = fluids.get(i);
			long capacity = container.getTankCapacity(i);
			list.addAll(fluid(fluid, capacity));
		}

		return list;
	}

	public static List<Component> fluid(FluidHolder fluid, long capacity)
	{
		String modid = ObjectRegistry.get(Registries.FLUID).getId(fluid.getFluid()).getNamespace();
		Component name = FluidHelper.getDisplayName(fluid);
		long amountMB = FluidConstants.toMillibuckets(fluid.getFluidAmount());
		long capacityMB = FluidConstants.toMillibuckets(capacity);
		MutableComponent storage = Component.translatable("gauge_text.ad_astra.liquid_storage", amountMB, capacityMB);

		List<Component> list = new ArrayList<>();
		list.add(storage.setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)).append(Component.nullToEmpty(", ")).append(name));
		list.add(Component.literal(ModHooks.getName(modid)).setStyle(Style.EMPTY.withItalic(true).withColor(ChatFormatting.BLUE)));
		return list;
	}

	public static Component formatPercent(double ratio)
	{
		return Component.literal(NUMBER_FORMAT.format(ratio * 100.0D)).append("%");
	}

	private TranslationUtils()
	{

	}

	private record CanUseTuple(boolean canUseOnCold, boolean canUseOnHot)
	{

	}

}
