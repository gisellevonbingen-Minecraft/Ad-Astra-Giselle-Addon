package ad_astra_giselle_addon.common;

import java.util.Collections;

import ad_astra_giselle_addon.common.command.AddonCommand;
import ad_astra_giselle_addon.common.delegate.CreativeModeTabBuilder;
import ad_astra_giselle_addon.common.delegate.DelegateFluidHelper;
import ad_astra_giselle_addon.common.delegate.DelegateLivingHelper;
import ad_astra_giselle_addon.common.delegate.DelegateProvider;
import ad_astra_giselle_addon.common.delegate.DelegateRegistryFactory;
import ad_astra_giselle_addon.common.delegate.DelegateScreenHelper;
import ad_astra_giselle_addon.common.delegate.FabricFluidHelper;
import ad_astra_giselle_addon.common.delegate.FabricRegisterFactory;
import ad_astra_giselle_addon.common.delegate.PlatformCommonDelegate;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.world.item.CreativeModeTab;

public class AdAstraGiselleAddonFabric implements ModInitializer, PlatformCommonDelegate
{
	@Override
	public void onInitialize()
	{
		AdAstraGiselleAddon.initCommon(this);
		CommandRegistrationCallback.EVENT.register((dispatcher, registry, selection) -> dispatcher.register(AddonCommand.builder()));
	}

	@Override
	public DelegateRegistryFactory getRegistryFactory()
	{
		return FabricRegisterFactory.INSTANCE;
	}

	@Override
	public CreativeModeTab createCreativeModeTab(CreativeModeTabBuilder builder)
	{
		return FabricItemGroupBuilder.create(builder.id()).icon(builder.icon()).appendItems(builder::appendItems).build();
	}

	@Override
	public DelegateLivingHelper getLivingHelper()
	{
		return living -> Collections.emptyList();
	}

	@Override
	public DelegateFluidHelper getFluidHelper()
	{
		return FabricFluidHelper.INTANCE;
	}

	@Override
	public DelegateProvider getAddonHelper()
	{
		return () -> Collections.emptyList();
	}

	@Override
	public DelegateScreenHelper getScreenHelper()
	{
		return () -> true;
	}

}
