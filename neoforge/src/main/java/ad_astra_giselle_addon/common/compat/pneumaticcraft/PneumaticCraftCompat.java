package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;

import ad_astra_giselle_addon.client.compat.pneumaticcraft.AddonPneumaticCraftCompatClient;
import ad_astra_giselle_addon.common.compat.CompatibleMod;
import ad_astra_giselle_addon.common.registry.AddonProofs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

public class PneumaticCraftCompat extends CompatibleMod
{
	public static final String MOD_ID = "pneumaticcraft";

	public static ResourceLocation rl(String path)
	{
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
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
		NeoForge.EVENT_BUS.register(handler);
		AddonProofs.OXYGEN.register(handler::onOxygenProof);
		AddonProofs.HOT_TEMPERATURE.register(handler::onHotTemperatureProof);
		AddonProofs.ACID_RAIN.register(handler::onAcidRainProof);
		AddonProofs.GRAVITY.register(handler::onGravityProof);

		if (FMLEnvironment.dist.isClient())
		{
			new AddonPneumaticCraftCompatClient();
		}

	}

	@Override
	public void collectEquipCommands(List<ArgumentBuilder<CommandSourceStack, ?>> list)
	{
		super.collectEquipCommands(list);
		list.add(Commands.literal("pneumatic_armor").executes(PneumaticCraftCommand::pneumatic_armor));
	}

}
