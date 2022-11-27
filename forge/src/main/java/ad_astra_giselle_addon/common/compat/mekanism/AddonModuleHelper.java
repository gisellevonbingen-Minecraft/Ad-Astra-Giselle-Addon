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
import net.minecraftforge.event.entity.EntityEvent;

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

	/**
	 *
	 * @param <T>
	 *            T extends mekanism.common.content.gear.Module
	 * @param e
	 *            Cancelable LivingEvent
	 * @param type
	 *            Module Type
	 * @param getEnergyUsing
	 *            Energy function for cancel
	 * @return Whether canceled in this method
	 */
	public static <T extends ICustomModule<T>> boolean tryCancel(EntityEvent e, IModuleDataProvider<T> type, @Nullable Function<T, FloatingLong> getEnergyUsing)
	{
		if (!e.isCancelable() || e.isCanceled() || !(e.getEntity() instanceof LivingEntity entity))
		{
			return false;
		}

		Module<T> module = AddonModuleHelper.findArmorEnabledModule(entity, type);

		if (module != null)
		{
			boolean cancel = false;

			if (entity instanceof Player player && !MekanismUtils.isPlayingMode(player))
			{
				cancel = true;
			}
			else if (getEnergyUsing != null)
			{
				FloatingLong usingEnergy = getEnergyUsing.apply(module.getCustomInstance());

				if (module.canUseEnergy(entity, usingEnergy))
				{
					if (!entity.getLevel().isClientSide())
					{
						module.useEnergy(entity, usingEnergy);
					}

					cancel = true;
				}

			}
			else
			{
				cancel = true;
			}

			if (cancel)
			{
				e.setCanceled(true);
				return true;
			}

		}

		return false;
	}

	private AddonModuleHelper()
	{

	}

}
