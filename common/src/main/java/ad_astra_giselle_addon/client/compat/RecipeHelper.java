package ad_astra_giselle_addon.client.compat;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.delegate.DelegateRegistry;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

public class RecipeHelper
{
	public static final String JEI_CATEGORY = "jei.category";
	public static final String JEI_INFO = "jei.info";
	public static final String JEI_TOOLTIP = "jei.tooltip";

	public static ResourceLocation createUid(ResourceLocation key)
	{
		return new ResourceLocation(key.getNamespace(), JEI_CATEGORY + "." + key.getPath());
	}

	public static Component getCategoryTitle(ResourceLocation key)
	{
		return Component.translatable(JEI_CATEGORY + "." + key.getNamespace() + "." + key.getPath());
	}

	public static Component getInfoTitle(ItemLike item)
	{
		return item.asItem().getDescription();
	}

	public static Component getInfoBody(ItemLike item, Object[] objects)
	{
		return Component.translatable(AdAstraGiselleAddon.tl(JEI_INFO, DelegateRegistry.get(Registry.ITEM_REGISTRY).getId(item.asItem())), objects);
	}

	public class FuelLoader
	{
		public static final ResourceLocation BACKGROUND_LOCATION = AdAstraGiselleAddon.rl("textures/jei/fuel_loader.png");
		public static final int BACKGROUND_WIDTH = 144;
		public static final int BACKGROUND_HEIGHT = 84;
		public static final int TANK_LEFT = 53;
		public static final int TANK_TOP = 20;

		public static boolean testFluid(Fluid fluid)
		{
			return FluidPredicates.isFuel(fluid) && fluid.isSource(fluid.defaultFluidState());
		}

	}

	private RecipeHelper()
	{

	}

}
