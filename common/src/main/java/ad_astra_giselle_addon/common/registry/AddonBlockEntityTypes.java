package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AddonBlockEntityTypes
{
	public static final BlockEntityTypeRegistryCollection BLOCK_ENTITY_TYPES = new BlockEntityTypeRegistryCollection(AdAstraGiselleAddon.MOD_ID);
	public static final ObjectRegistryHolder<BlockEntityType<FuelLoaderBlockEntity>> FUEL_LOADER = BLOCK_ENTITY_TYPES.add("fuel_loader", AddonBlocks.FUEL_LOADER, FuelLoaderBlockEntity::new);

	private AddonBlockEntityTypes()
	{

	}

}
