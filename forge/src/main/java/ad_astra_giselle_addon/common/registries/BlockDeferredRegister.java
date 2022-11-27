package ad_astra_giselle_addon.common.registries;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockDeferredRegister extends DoubleDeferredRegister<Block, Item>
{
	public BlockDeferredRegister(String modid)
	{
		super(modid, ForgeRegistries.BLOCKS, ForgeRegistries.ITEMS);
	}

	public <B extends Block> BlockRegistryObject<B, BlockItem> registerDefaultProperties(String name, Supplier<? extends B> blockSup)
	{
		return this.register(name, blockSup, b -> new BlockItem(b, new Item.Properties()));
	}

	public <B extends Block> BlockRegistryObject<B, BlockItem> registerDefaultProperties(String name, Supplier<? extends B> blockSup, UnaryOperator<Item.Properties> propertiesOperator)
	{
		return this.register(name, blockSup, b ->
		{
			return new BlockItem(b, propertiesOperator.apply(new Item.Properties()));
		});
	}

	public <B extends Block, I extends BlockItem> BlockRegistryObject<B, I> register(String name, Supplier<? extends B> blockSup, Function<B, ? extends I> itemFunc)
	{
		return this.register(name, blockSup, itemFunc, BlockRegistryObject::new);
	}

}
