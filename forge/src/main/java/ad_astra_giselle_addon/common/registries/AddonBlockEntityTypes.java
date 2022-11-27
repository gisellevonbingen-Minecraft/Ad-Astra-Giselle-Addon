package ad_astra_giselle_addon.common.registries;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class AddonBlockEntityTypes
{
	public static final BlockEntityTypeDeferredRegister BLOCK_ENTITY_TYPES = new BlockEntityTypeDeferredRegister(AdAstraGiselleAddon.MOD_ID);
	public static final RegistryObject<BlockEntityType<FuelLoaderBlockEntity>> FUEL_LOADER = BLOCK_ENTITY_TYPES.register("fuel_loader", AddonBlocks.FUEL_LOADER, FuelLoaderBlockEntity::new);

	private AddonBlockEntityTypes()
	{

	}

}
