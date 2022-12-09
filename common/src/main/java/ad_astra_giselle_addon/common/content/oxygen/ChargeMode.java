package ad_astra_giselle_addon.common.content.oxygen;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public enum ChargeMode implements IChargeMode
{
	NONE(AdAstraGiselleAddon.rl("none"), e -> Collections.emptyList()),
	ARMORS(AdAstraGiselleAddon.rl("armors"), LivingHelper::getArmorItems),
	ALL(AdAstraGiselleAddon.rl("all"), LivingHelper::getInventoryStacks),
	//
	;

	private final ResourceLocation name;
	private final Function<LivingEntity, List<ItemStackReference>> function;
	private final Component displayName;

	private ChargeMode(ResourceLocation name, Function<LivingEntity, List<ItemStackReference>> function)
	{
		this.name = name;
		this.function = function;
		this.displayName = IChargeMode.createDisplayName(name);
	}

	@Override
	public ResourceLocation getName()
	{
		return this.name;
	}

	public Function<LivingEntity, List<ItemStackReference>> getFunction()
	{
		return this.function;
	}

	@Override
	public List<ItemStackReference> getItems(LivingEntity living)
	{
		return this.getFunction().apply(living);
	}

	@Override
	public Component getDisplayName()
	{
		return this.displayName;
	}

}
