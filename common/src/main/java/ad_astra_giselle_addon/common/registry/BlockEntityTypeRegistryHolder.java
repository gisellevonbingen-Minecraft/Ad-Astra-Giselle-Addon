package ad_astra_giselle_addon.common.registry;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityTypeRegistryHolder<BE extends BlockEntity> extends ObjectRegistryHolder<BlockEntityType<BE>>
{
	public BlockEntityTypeRegistryHolder(ResourceLocation id, Supplier<BlockEntityType<BE>> initializer, ResourceKey<? extends Registry<?>> key)
	{
		super(id, initializer, key);
	}

}
