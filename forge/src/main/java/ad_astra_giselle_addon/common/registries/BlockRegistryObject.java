package ad_astra_giselle_addon.common.registries;

import java.util.function.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistryObject<B extends Block, I extends BlockItem> extends DoubleRegistryObject<B, I> implements Supplier<B>, ItemLike
{
	public BlockRegistryObject(RegistryObject<? extends B> b, RegistryObject<? extends I> i)
	{
		super(b, i);
	}

	public B asBlock()
	{
		return this.getPrimary();
	}

	@Override
	public B get()
	{
		return this.asBlock();
	}

	@Override
	public I asItem()
	{
		return this.getSecondary();
	}

}
