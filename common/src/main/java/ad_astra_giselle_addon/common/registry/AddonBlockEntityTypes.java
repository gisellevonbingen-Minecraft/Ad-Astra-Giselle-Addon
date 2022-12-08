package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.entity.FuelLoaderBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AddonBlockEntityTypes
{
	public static final DelegateBlockEntityTypeCollection BLOCK_ENTITY_TYPES = new DelegateBlockEntityTypeCollection(AdAstraGiselleAddon.MOD_ID);
	public static final DelegateObjectHolder<BlockEntityType<FuelLoaderBlockEntity>> FUEL_LOADER = BLOCK_ENTITY_TYPES.register("fuel_loader", AddonBlocks.FUEL_LOADER, FuelLoaderBlockEntity::new);

	private AddonBlockEntityTypes()
	{

	}

}
