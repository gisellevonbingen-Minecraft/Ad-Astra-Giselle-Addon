package ad_astra_giselle_addon.client.screen;

import java.util.List;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import ad_astra_giselle_addon.common.menu.FuelLoaderMenu;
import ad_astra_giselle_addon.common.util.TranslationUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FuelLoaderScreen extends AddonMachineScreen<FuelLoaderMenu, FuelLoaderBlockEntity>
{
	public static final ResourceLocation TEXTURE = AdAstraGiselleAddon.rl("textures/gui/container/fuel_loader.png");
	public static final int WIDTH = 176;
	public static final int HEIGHT = 182;

	public FuelLoaderScreen(FuelLoaderMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title, TEXTURE, STEEL_SLOT, WIDTH, HEIGHT);
	}

	public List<Component> getFluidBarTooltip()
	{
		return TranslationUtils.fluid(this.entity.getFluidContainer());
	}

}
