package ad_astra_giselle_addon.common.compat.mekanism;

import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;

import ad_astra_giselle_addon.common.compat.CompatibleMod;
import ad_astra_giselle_addon.common.content.proof.AcidRainProofUtils;
import ad_astra_giselle_addon.common.content.proof.GravityNormalizingUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceFireProofUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class MekanismCompat extends CompatibleMod
{
	public static final String MOD_ID = "mekanism";

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}

	@Override
	public String getModId()
	{
		return MOD_ID;
	}

	@Override
	protected void onLoad()
	{
		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		AddonMekanismModules.MODULES.register(fml_bus);
		AddonMekanismItems.ITEMS.register(fml_bus);
		fml_bus.register(MekanismFMLEventListener.class);

		MekanismProofProvidingHandler handler = new MekanismProofProvidingHandler();
		SpaceOxygenProofUtils.INSTANCE.register(handler::onLivingSpaceOxygenProof);
		SpaceFireProofUtils.INSTANCE.register(handler::onLivingSpaceFireProof);
		AcidRainProofUtils.INSTANCE.register(handler::onLivingVenusAcidProof);
		GravityNormalizingUtils.INSTANCE.register(handler::onLivingGravityNormalizing);
	}

	@Override
	public void collectEquipCommands(List<ArgumentBuilder<CommandSourceStack, ?>> list)
	{
		super.collectEquipCommands(list);
		list.add(Commands.literal("mekasuit").executes(MekanismCommand::mekasuit));
	}

}
