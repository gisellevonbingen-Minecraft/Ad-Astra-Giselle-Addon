package ad_astra_giselle_addon.client.mixin.mixin.ad_astra;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import earth.terrarium.adastra.client.components.machines.ConfigurationWidget;
import earth.terrarium.adastra.common.menus.configuration.MenuConfiguration;

@Mixin(value = ConfigurationWidget.class)
public interface ConfigurationWidgetAccessor
{
	@Accessor(remap = false)
	MenuConfiguration getConfiguration();
}
