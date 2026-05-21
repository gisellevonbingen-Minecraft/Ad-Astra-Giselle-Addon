package ad_astra_giselle_addon.common.compat.trinkets;

import java.util.Map;
import java.util.Map.Entry;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;

public class TrinketsHelper
{
	public static Container getEquippedTrinkets(LivingEntity entity)
	{
		TrinketComponent component = TrinketsApi.getTrinketComponent(entity).orElse(null);
		Container container = null;

		for (Entry<String, Map<String, TrinketInventory>> entry : component.getInventory().entrySet())
		{
			for (TrinketInventory inventory : entry.getValue().values())
			{
				if (container == null)
				{
					container = inventory;
				}
				else
				{
					container = new CompoundContainer(container, inventory);
				}

			}

		}

		return container;
	}

	private TrinketsHelper()
	{

	}

}
