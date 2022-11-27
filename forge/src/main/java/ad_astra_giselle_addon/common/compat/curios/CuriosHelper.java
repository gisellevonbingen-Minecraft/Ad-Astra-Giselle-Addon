package ad_astra_giselle_addon.common.compat.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosHelper
{
	public static IItemHandlerModifiable getEquippedCurios(LivingEntity entity)
	{
		return CuriosApi.getCuriosHelper().getEquippedCurios(entity).orElse(null);
	}

	private CuriosHelper()
	{

	}

}
