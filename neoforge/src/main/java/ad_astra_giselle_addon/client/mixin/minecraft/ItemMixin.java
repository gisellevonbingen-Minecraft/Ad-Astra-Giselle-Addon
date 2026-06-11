package ad_astra_giselle_addon.client.mixin.minecraft;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ad_astra_giselle_addon.client.AdAstraGiselleAddonClient;
import ad_astra_giselle_addon.common.item.IClientExtensionItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

@Mixin(value = Item.class)
public abstract class ItemMixin
{
	@Inject(method = "initializeClient", at = @At("TAIL"), remap = false)
	private void initializeClient(Consumer<IClientItemExtensions> consumer, CallbackInfo ci)
	{
		if (this instanceof IClientExtensionItem item)
		{
			consumer.accept(new IClientItemExtensions()
			{
				@Override
				public BlockEntityWithoutLevelRenderer getCustomRenderer()
				{
					return AdAstraGiselleAddonClient.getItemRenderer(item);
				}
			});
		}

	}

}
