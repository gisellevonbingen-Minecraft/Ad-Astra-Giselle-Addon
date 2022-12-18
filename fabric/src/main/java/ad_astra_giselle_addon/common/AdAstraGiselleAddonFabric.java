package ad_astra_giselle_addon.common;

import ad_astra_giselle_addon.common.config.AddonConfigs;
import ad_astra_giselle_addon.common.item.SidedInvWrapperSlot;
import ad_astra_giselle_addon.common.item.SidedItemContainerBlock;
import ad_astra_giselle_addon.common.registry.AddonBlockEntityTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AdAstraGiselleAddonFabric implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		AdAstraGiselleAddon.initializeCommon();
		AdAstraGiselleAddon.registerConfig(AddonConfigs.class);
		CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> AdAstraGiselleAddon.registerCommand(dispatcher::register));

		ItemStorage.SIDED.registerForBlockEntities((blockEntity, direction) ->
		{
			if (blockEntity instanceof SidedItemContainerBlock itemContainer)
			{
				return SidedInvWrapperSlot.of(itemContainer, direction);
			}

			return null;
		}, AddonBlockEntityTypes.BLOCK_ENTITY_TYPES.getValues().toArray(new BlockEntityType[0]));

		ItemStorage.SIDED.registerFallback((world, pos, state, blockEntity, direction) ->
		{
			if (blockEntity instanceof SidedItemContainerBlock itemContainer)
			{
				return SidedInvWrapperSlot.of(itemContainer, direction);
			}

			return null;
		});
	}

}
