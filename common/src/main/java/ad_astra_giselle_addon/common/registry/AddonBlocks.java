package ad_astra_giselle_addon.common.registry;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.block.AutomationNasaWorkbenchBlock;
import ad_astra_giselle_addon.common.block.FuelLoaderBlock;
import ad_astra_giselle_addon.common.block.GravityNormalizerBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class AddonBlocks
{
	public static final BlockRegistryCollection BLOCKS = new BlockRegistryCollection(AdAstraGiselleAddon.MOD_ID);
	public static final BlockRegistryHolder<FuelLoaderBlock, BlockItem> FUEL_LOADER = BLOCKS.add("fuel_loader", () -> new FuelLoaderBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);
	public static final BlockRegistryHolder<AutomationNasaWorkbenchBlock, BlockItem> AUTOMATION_NASA_WORKBENCH = BLOCKS.add("automation_nasa_workbench", () -> new AutomationNasaWorkbenchBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);
	public static final BlockRegistryHolder<GravityNormalizerBlock, BlockItem> GRAVITY_NORMALIZER = BLOCKS.add("gravity_normalizer", () -> new GravityNormalizerBlock(createDefaultBlockProperties()), AddonBlocks::createBlockItem);

	public static BlockBehaviour.Properties createDefaultBlockProperties()
	{
		return BlockBehaviour.Properties.of().strength(3.0F);
	}

	public static BlockItem createBlockItem(Block block)
	{
		return new BlockItem(block, AddonItems.getMainItemProperties());
	}

	private AddonBlocks()
	{

	}

}
