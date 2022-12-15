package ad_astra_giselle_addon.common.registry;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockRegistryCollection extends DoubleRegistryCollection<Block, Item>
{
	public BlockRegistryCollection(String modid)
	{
		super(modid, Registry.BLOCK_REGISTRY, Registry.ITEM_REGISTRY);
	}

	public <B extends Block> BlockRegistryHolder<B, BlockItem> addDefaultProperties(String name, Supplier<B> blockSup)
	{
		return this.add(name, blockSup, b -> new BlockItem(b, new Item.Properties()));
	}

	public <B extends Block> BlockRegistryHolder<B, BlockItem> addDefaultProperties(String name, Supplier<B> blockSup, UnaryOperator<Item.Properties> propertiesOperator)
	{
		return this.add(name, blockSup, b ->
		{
			return new BlockItem(b, propertiesOperator.apply(new Item.Properties()));
		});
	}

	public <B extends Block, I extends BlockItem> BlockRegistryHolder<B, I> add(String name, Supplier<B> blockSup, Function<B, I> itemFunc)
	{
		return this.add(name, blockSup, itemFunc, BlockRegistryHolder::new);
	}

}
