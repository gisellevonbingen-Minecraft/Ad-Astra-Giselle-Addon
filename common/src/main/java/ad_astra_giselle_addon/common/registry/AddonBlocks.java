package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.FuelLoaderBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class AddonBlocks
{
	public static final DelegateBlockCollection BLOCKS = new DelegateBlockCollection(AdAstraGiselleAddon.MOD_ID);
	public static final DelegateBlockHolder<FuelLoaderBlock, BlockItem> FUEL_LOADER = BLOCKS.add("fuel_loader", () -> new FuelLoaderBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);

	public static BlockBehaviour.Properties createDefaultBlockProperties()
	{
		return BlockBehaviour.Properties.of(Material.METAL).strength(3.0F);
	}

	public static BlockItem createBlockItem(Block block)
	{
		return new BlockItem(block, AddonItems.getMainItemProperties());
	}

	private AddonBlocks()
	{

	}

}
