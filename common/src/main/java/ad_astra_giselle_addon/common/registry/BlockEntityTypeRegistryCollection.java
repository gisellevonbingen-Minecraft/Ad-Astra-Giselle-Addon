package ad_astra_giselle_addon.common.registry;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityTypeRegistryCollection extends ObjectRegistryCollection<BlockEntityType<?>>
{
	public BlockEntityTypeRegistryCollection(String modid)
	{
		super(modid, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
	}

	private <BE extends BlockEntity> Supplier<? extends BlockEntityType<BE>> getBuilder(BlockRegistryHolder<?, ?> block, BlockEntityType.BlockEntitySupplier<BE> blockEntitySupplier)
	{
		return () -> BlockEntityType.Builder.of(blockEntitySupplier, block.get()).build(null);
	}

	public <BE extends BlockEntity> BlockEntityTypeRegistryHolder<BE> add(String name, BlockRegistryHolder<?, ?> block, BlockEntityType.BlockEntitySupplier<BE> blockEntitySupplier)
	{
		return this.add(name, this.getBuilder(block, blockEntitySupplier), BlockEntityTypeRegistryHolder<BE>::new);
	}

}
