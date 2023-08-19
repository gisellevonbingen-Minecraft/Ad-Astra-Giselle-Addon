package ad_astra_giselle_addon.common.block.entity;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface IRocketSensingType
{
	public static final String LANGUGE_CATEGORY_ROCKET_SENSING_TYPE = "rocket_sensing_type";

	public static List<IRocketSensingType> getRocketSensingTypes()
	{
		return Arrays.asList(RocketSensingType.values());
	}

	@NotNull
	public static IRocketSensingType find(@Nullable ResourceLocation name)
	{
		if (name == null)
		{
			return RocketSensingType.DISABLED;
		}

		for (IRocketSensingType mode : getRocketSensingTypes())
		{
			if (mode.getName().equals(name))
			{
				return mode;
			}

		}

		return RocketSensingType.DISABLED;
	}

	public static Tag writeNBT(@Nullable IRocketSensingType mode)
	{
		ResourceLocation name = (mode != null ? mode : RocketSensingType.DISABLED).getName();
		return StringTag.valueOf(name.toString());
	}

	@NotNull
	public static IRocketSensingType readNBT(@Nullable Tag tag)
	{
		if (tag == null)
		{
			return RocketSensingType.DISABLED;
		}

		ResourceLocation name = ResourceLocation.tryParse(tag.getAsString());
		return find(name);
	}

	public ResourceLocation getName();

	public Component getDisplayName();

	public ItemStack getDisplayIcon();

	public List<Component> getTooltip();

	public int getAnalogSignal(Rocket rocket);

}
