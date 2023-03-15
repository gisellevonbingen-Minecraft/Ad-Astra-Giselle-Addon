package ad_astra_giselle_addon.common.fluid;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.network.chat.Component;

public class FluidHelperDelegate implements FluidHelper.Delegate
{
	@Override
	public Component getDisplayName(FluidHolder fluid)
	{
		FluidVariant variant = FluidVariant.of(fluid.getFluid(), fluid.getCompound());
		return FluidVariantAttributes.getName(variant);
	}

}
