package ad_astra_giselle_addon.client.mixin.minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.resources.ResourceLocation;

@Mixin(value = AbstractSliderButton.class)
public interface AbstractSliderButtonInvoker
{
	@Invoker
	ResourceLocation invokeGetSprite();

	@Invoker
	ResourceLocation invokeGetHandleSprite();
}
