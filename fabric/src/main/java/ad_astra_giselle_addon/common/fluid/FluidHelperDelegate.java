package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.network.chat.Component;

public class FluidHelperDelegate implements FluidHelper.Delegate
{
	@Override
	public Component getDisplayName(ResourceStack<FluidResource> fluid)
	{
		FluidResource resource = fluid.resource();
		FluidVariant variant = FluidVariant.of(resource.getType(), resource.getDataPatch());
		return FluidVariantAttributes.getName(variant);
	}

}
