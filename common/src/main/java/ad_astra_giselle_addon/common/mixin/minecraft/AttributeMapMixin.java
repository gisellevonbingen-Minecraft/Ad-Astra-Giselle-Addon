package ad_astra_giselle_addon.common.mixin.minecraft;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.collect.Maps;

import ad_astra_giselle_addon.common.registries.AddonAttributes;
import ad_astra_giselle_addon.common.registries.DelegateObjectHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

@Mixin(AttributeMap.class)
public class AttributeMapMixin
{
	@Shadow
	private final Map<Attribute, AttributeInstance> attributes = Maps.newHashMap();

	@Shadow
	private void onAttributeModified(AttributeInstance instance)
	{

	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(CallbackInfo callbackInfo)
	{
		for (DelegateObjectHolder<Attribute> holder : AddonAttributes.ATTRIBUTES.getObjects())
		{
			Attribute attribute = holder.get();
			this.attributes.computeIfAbsent(attribute, key -> new AttributeInstance(key, this::onAttributeModified));
		}

	}

}
