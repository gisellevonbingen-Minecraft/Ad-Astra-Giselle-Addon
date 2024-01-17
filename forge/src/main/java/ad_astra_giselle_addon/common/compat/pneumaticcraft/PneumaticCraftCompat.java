package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;

import ad_astra_giselle_addon.client.compat.pneumaticcraft.AddonPneumaticCraftCompatClient;
import ad_astra_giselle_addon.common.compat.CompatibleMod;
import ad_astra_giselle_addon.common.content.proof.AcidRainProofUtils;
import ad_astra_giselle_addon.common.content.proof.GravityNormalizingUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceFireProofUtils;
import ad_astra_giselle_addon.common.content.proof.SpaceOxygenProofUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;

public class PneumaticCraftCompat extends CompatibleMod
{
	public static final String MOD_ID = "pneumaticcraft";

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
		AddonPNCUpgrades.UPGRADES.register();
		AddonCommonUpgradeHandlers.register();

		PneumaticCraftProofProvidingHandler handler = new PneumaticCraftProofProvidingHandler();
		MinecraftForge.EVENT_BUS.register(handler);
		SpaceOxygenProofUtils.INSTANCE.register(handler::onLivingSpaceOxygenProof);
		SpaceFireProofUtils.INSTANCE.register(handler::onLivingSpaceFireProof);
		AcidRainProofUtils.INSTANCE.register(handler::onLivingVenusAcidProof);
		GravityNormalizingUtils.INSTANCE.register(handler::onLivingGravityNormalizing);

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> AddonPneumaticCraftCompatClient::new);
	}

	@Override
	public void collectEquipCommands(List<ArgumentBuilder<CommandSourceStack, ?>> list)
	{
		super.collectEquipCommands(list);
		list.add(Commands.literal("pneumatic_armor").executes(PneumaticCraftCommand::pneumatic_armor));
	}

}
