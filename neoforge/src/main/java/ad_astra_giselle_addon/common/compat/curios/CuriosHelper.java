package ad_astra_giselle_addon.common.compat.curios;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class CuriosHelper
{
	@Nullable
	public static IItemHandlerModifiable getEquippedCurios(LivingEntity entity)
	{
		return CuriosApi.getCuriosInventory(entity).map(ICuriosItemHandler::getEquippedCurios).orElse(null);
	}

	private CuriosHelper()
	{

	}

}
