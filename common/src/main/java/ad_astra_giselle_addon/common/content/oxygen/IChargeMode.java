package ad_astra_giselle_addon.common.content.oxygen;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ad_astra_giselle_addon.common.item.ItemStackReference;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public interface IChargeMode
{
	public static final String LANGUGE_CATEGORY_CHARGEMODE = "chargemode";

	public static List<IChargeMode> getAllChargeModes()
	{
		return Arrays.asList(ChargeMode.values());
	}

	@NotNull
	public static IChargeMode find(@Nullable ResourceLocation name)
	{
		if (name == null)
		{
			return ChargeMode.NONE;
		}

		for (IChargeMode mode : getAllChargeModes())
		{
			if (mode.getName().equals(name))
			{
				return mode;
			}

		}

		return ChargeMode.NONE;
	}

	public static Tag writeNBT(@Nullable IChargeMode mode)
	{
		ResourceLocation name = (mode != null ? mode : ChargeMode.NONE).getName();
		return StringTag.valueOf(name.toString());
	}

	@NotNull
	public static IChargeMode readNBT(@Nullable Tag tag)
	{
		if (tag == null)
		{
			return ChargeMode.NONE;
		}

		ResourceLocation name = ResourceLocation.tryParse(tag.getAsString());
		return find(name);
	}

	public ResourceLocation getName();

	public Component getDisplayName();

	public List<ItemStackReference> getItems(LivingEntity living);
}
