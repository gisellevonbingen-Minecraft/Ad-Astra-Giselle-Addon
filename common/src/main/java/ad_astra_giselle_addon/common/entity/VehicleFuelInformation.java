package ad_astra_giselle_addon.common.entity;

import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public record VehicleFuelInformation(CommonStorage<FluidResource> fuelTank, TagKey<Fluid> fuelTag)
{

}
