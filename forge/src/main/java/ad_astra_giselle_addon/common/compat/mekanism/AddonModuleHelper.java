package ad_astra_giselle_addon.common.compat.mekanism;

import java.util.function.Function;

import javax.annotation.Nullable;

import mekanism.api.gear.ICustomModule;
import mekanism.api.math.FloatingLong;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.common.content.gear.Module;
import mekanism.common.content.gear.ModuleHelper;
import mekanism.common.util.MekanismUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AddonModuleHelper
{
	public static <T extends ICustomModule<T>> Module<T> findArmorEnabledModule(Entity entity, IModuleDataProvider<T> type)
	{
		for (ItemStack itemStack : entity.getArmorSlots())
		{
			Module<T> module = ModuleHelper.INSTANCE.load(itemStack, type);

			if (module != null && module.isEnabled())
			{
				return module;
			}

		}

		return null;
	}

	public static <T extends ICustomModule<T>> boolean useEnergy(LivingEntity living, IModuleDataProvider<T> type, @Nullable Function<T, FloatingLong> getEnergyUsing)
	{
		Module<T> module = AddonModuleHelper.findArmorEnabledModule(living, type);

		if (module == null)
		{
			return false;
		}
		else if (living instanceof Player player && !MekanismUtils.isPlayingMode(player))
		{
			return true;
		}
		else if (getEnergyUsing == null)
		{
			return true;
		}
		else
		{
			FloatingLong usingEnergy = getEnergyUsing.apply(module.getCustomInstance());

			if (module.canUseEnergy(living, usingEnergy))
			{
				if (!living.getLevel().isClientSide())
				{
					module.useEnergy(living, usingEnergy);
				}

				return true;
			}

			return false;
		}

	}

	private AddonModuleHelper()
	{

	}

}
