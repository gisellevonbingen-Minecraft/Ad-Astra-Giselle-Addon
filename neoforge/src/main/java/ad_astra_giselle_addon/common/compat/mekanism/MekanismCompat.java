package ad_astra_giselle_addon.common.compat.mekanism;

import java.util.List;

import com.mojang.brigadier.builder.ArgumentBuilder;

import ad_astra_giselle_addon.common.compat.CompatibleMod;
import ad_astra_giselle_addon.common.registry.AddonProofs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;

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
		IEventBus fml_bus = ModLoadingContext.get().getActiveContainer().getEventBus();
		AddonMekanismModules.MODULES.register(fml_bus);
		AddonMekanismItems.ITEMS.register(fml_bus);
		fml_bus.register(MekanismFMLEventListener.class);

		MekanismProofProvidingHandler handler = new MekanismProofProvidingHandler();
		AddonProofs.OXYGEN.register(handler::onOxygenProof);
		AddonProofs.HOT_TEMPERATURE.register(handler::onHotTemperatureProof);
		AddonProofs.ACID_RAIN.register(handler::onAcidRainProof);
		AddonProofs.GRAVITY.register(handler::onGravityProof);
	}

	@Override
	public void collectEquipCommands(List<ArgumentBuilder<CommandSourceStack, ?>> list)
	{
		super.collectEquipCommands(list);
		list.add(Commands.literal("mekasuit").executes(MekanismCommand::mekasuit));
	}

}
