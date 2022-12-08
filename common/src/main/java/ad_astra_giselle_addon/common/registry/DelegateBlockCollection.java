package ad_astra_giselle_addon.common.registry;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class DelegateBlockCollection extends DelegateDoubleCollection<Block, Item>
{
	public DelegateBlockCollection(String modid)
	{
		super(modid, Registry.BLOCK_REGISTRY, Registry.ITEM_REGISTRY);
	}

	public <B extends Block> DelegateBlockHolder<B, BlockItem> registerDefaultProperties(String name, Supplier<? extends B> blockSup)
	{
		return this.register(name, blockSup, b -> new BlockItem(b, new Item.Properties()));
	}

	public <B extends Block> DelegateBlockHolder<B, BlockItem> registerDefaultProperties(String name, Supplier<? extends B> blockSup, UnaryOperator<Item.Properties> propertiesOperator)
	{
		return this.register(name, blockSup, b ->
		{
			return new BlockItem(b, propertiesOperator.apply(new Item.Properties()));
		});
	}

	public <B extends Block, I extends BlockItem> DelegateBlockHolder<B, I> register(String name, Supplier<? extends B> blockSup, Function<B, ? extends I> itemFunc)
	{
		return this.register(name, blockSup, itemFunc, DelegateBlockHolder::new);
	}

}
