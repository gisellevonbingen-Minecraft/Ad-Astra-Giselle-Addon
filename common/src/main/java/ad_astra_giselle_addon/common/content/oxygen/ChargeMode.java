package ad_astra_giselle_addon.common.content.oxygen;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public enum ChargeMode implements IChargeMode
{
	NONE(AdAstraGiselleAddon.rl("none"), e -> Collections.emptyList(), slot -> false),
	ARMORS(AdAstraGiselleAddon.rl("armors"), LivingHelper::getEquipmentSlots, slot -> true),
	ALL(AdAstraGiselleAddon.rl("all"), LivingHelper::getInventorySlots, slot -> true),
	//
	;

	private final ResourceLocation name;
	private final Function<Player, List<StorageSlotContext>> function;
	private final Predicate<EquipmentSlot> predicate;
	private final Component displayName;

	private ChargeMode(ResourceLocation name, Function<Player, List<StorageSlotContext>> function, Predicate<EquipmentSlot> predicate)
	{
		this.name = name;
		this.function = function;
		this.predicate = predicate;
		this.displayName = Component.translatable(AdAstraGiselleAddon.tl(LANGUGE_CATEGORY_CHARGEMODE, name));
	}

	@Override
	public ResourceLocation getName()
	{
		return this.name;
	}

	@Override
	public List<StorageSlotContext> getSlots(Player living)
	{
		return this.function.apply(living);
	}

	@Override
	public boolean contains(EquipmentSlot slot)
	{
		return this.predicate.test(slot);
	}

	@Override
	public Component getDisplayName()
	{
		return this.displayName;
	}

}
