package ad_astra_giselle_addon.common.compat.mekanism;

import java.util.function.ToLongFunction;

import javax.annotation.Nullable;

import ad_astra_giselle_addon.common.entity.LivingHelper;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.gear.ModuleData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AddonModuleHelper
{
	public static <T extends ICustomModule<T>> ItemStackWithModule<T> findArmorEnabledModule(LivingEntity entity, DeferredHolder<ModuleData<?>, ModuleData<T>> type)
	{
		for (ItemStack itemStack : entity.getArmorSlots())
		{
			IModule<T> module = IModuleHelper.INSTANCE.getModule(itemStack, type);

			if (module != null && module.isEnabled())
			{
				return new ItemStackWithModule<>(itemStack, module);
			}

		}

		return null;
	}

	public record ItemStackWithModule<T extends ICustomModule<T>>(ItemStack stack, IModule<T> module)
	{

	}

	public static <T extends ICustomModule<T>> boolean useEnergy(LivingEntity living, DeferredHolder<ModuleData<?>, ModuleData<T>> type, @Nullable ToLongFunction<T> getEnergyUsing)
	{
		ItemStackWithModule<T> tuple = AddonModuleHelper.findArmorEnabledModule(living, type);

		if (tuple == null)
		{
			return false;
		}
		else if (!LivingHelper.isPlayingMode(living))
		{
			return true;
		}
		else if (getEnergyUsing == null)
		{
			return true;
		}
		else
		{
			ItemStack stack = tuple.stack();
			IModule<T> module = tuple.module();
			long usingEnergy = getEnergyUsing.applyAsLong(module.getCustomInstance());

			if (module.canUseEnergy(living, stack, usingEnergy))
			{
				if (!living.level().isClientSide())
				{
					module.useEnergy(living, stack, usingEnergy);
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
