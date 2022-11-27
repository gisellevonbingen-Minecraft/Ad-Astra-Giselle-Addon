package ad_astra_giselle_addon.common.registries;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityTypeDeferredRegister extends DeferredRegisterWrapper<BlockEntityType<?>>
{
	public BlockEntityTypeDeferredRegister(String modid)
	{
		super(modid, ForgeRegistries.BLOCK_ENTITY_TYPES);
	}

	public <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockRegistryObject<?, ?> block, BlockEntityType.BlockEntitySupplier<T> blockEntitySupplier)
	{
		return this.register(name, () -> BlockEntityType.Builder.of(blockEntitySupplier, block.get()).build(null));
	}

}
