package ad_astra_giselle_addon.common.compat.mekanism.gear;

import java.util.Map;
import java.util.function.Consumer;

import ad_astra_giselle_addon.client.overlay.OxygenCanOverlay;
import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenStorage;
import ad_astra_giselle_addon.common.content.oxygen.OxygenStorageUtils;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import mekanism.api.Action;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IHUDElement.HUDColor;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleContainer;
import mekanism.api.gear.IModuleHelper;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.FluidInDetails;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;

public class ModuleOxygenProofUnit implements ICustomModule<ModuleOxygenProofUnit>
{
	public static final ResourceLocation ICON = AdAstraGiselleAddon.rl(MekanismUtils.ResourceType.GUI_HUD.getPrefix() + "space_breathing_unit.png");

	private long energyUsingProduce;

	public ModuleOxygenProofUnit()
	{
		this.energyUsingProduce = AddonMekanismConfig.MODULES_OXYGEN_PROOF.energyUsingProduce;
	}

	@Override
	public void tickServer(IModule<ModuleOxygenProofUnit> module, IModuleContainer container, ItemStack stack, Player player)
	{
		ICustomModule.super.tickServer(module, container, stack, player);

		this.produceOxygen(module, container, stack, player);
	}

	private void produceOxygen(IModule<ModuleOxygenProofUnit> module, IModuleContainer container, ItemStack stack, Player player)
	{
		long productionRate = this.getProduceRate(module, player);

		if (productionRate == 0)
		{
			return;
		}

		long energyUsing = this.getEnergyUsingProduce();
		productionRate = Math.min(productionRate, module.getContainerEnergy(stack) / energyUsing);
		long productionRateFirst = productionRate;

		ItemStack handStack = player.getItemBySlot(EquipmentSlot.MAINHAND);
		IChemicalHandler handCapability = handStack.getCapability(Capabilities.CHEMICAL.item());

		if (handCapability != null)
		{
			ChemicalStack remain = handCapability.insertChemical(MekanismChemicals.OXYGEN.asStack(productionRate), Action.EXECUTE);
			productionRate = remain.getAmount();
		}

		productionRate = OxygenStorageUtils.insert(player, productionRate);

		long oxygenUsed = productionRateFirst - productionRate;
		long multiply = energyUsing * oxygenUsed;

		if (!player.level().isClientSide())
		{
			module.useEnergy(player, stack, multiply);
		}

		int airSupply = player.getAirSupply();
		int airFill = (int) Math.min(productionRateFirst, player.getMaxAirSupply() - airSupply);
		player.setAirSupply(airSupply + airFill);
	}

	public long getProduceRate(IModule<ModuleOxygenProofUnit> module, Player player)
	{
		Map<FluidType, FluidInDetails> fluidsIn = MekanismUtils.getFluidsIn(player, player.getEyeHeight(), (bb, data) ->
		{
			double centerX = (bb.minX + bb.maxX) / 2;
			double centerZ = (bb.minZ + bb.maxZ) / 2;
			return new AABB(centerX, Math.min(bb.minY + data - 0.27, bb.maxY), centerZ, centerX, Math.min(bb.minY + data - 0.14, bb.maxY), centerZ);
		});
		if (fluidsIn.entrySet().stream().anyMatch(entry -> entry.getKey() == NeoForgeMod.WATER_TYPE.value() && entry.getValue().getMaxHeight() >= 0.11))
		{
			return this.getMaxProduceRate(module);
		}
		else if (player.isInWaterOrRain())
		{
			return this.getMaxProduceRate(module) / 2L;
		}

		return 0L;
	}

	public boolean useResources(IModule<ModuleOxygenProofUnit> module, Player living, boolean simulate)
	{
		if (!LivingHelper.isPlayingMode(living))
		{
			return true;
		}

		long oxygenUsing = ProofAbstractUtils.OXYGEN_PROOF_USING;
		IOxygenStorage oxygenStorage = OxygenStorageUtils.firstExtractable(living, oxygenUsing);

		if (oxygenStorage != null)
		{
			if (oxygenStorage.extractOxygen(living, oxygenUsing, true) >= oxygenUsing)
			{
				if (!simulate && !living.level().isClientSide())
				{
					oxygenStorage.extractOxygen(living, oxygenUsing, false);
				}

				return true;
			}

		}

		return false;
	}

	@Override
	public void addHUDElements(IModule<ModuleOxygenProofUnit> module, IModuleContainer container, ItemStack stack, Player player, Consumer<IHUDElement> hudElementAdder)
	{
		ICustomModule.super.addHUDElements(module, container, stack, player, hudElementAdder);

		if (!module.isEnabled())
		{
			return;
		}

		double ratio = OxygenStorageUtils.getStoredRatio(player).orElse(0.0D);

		if (ratio == Double.POSITIVE_INFINITY)
		{
			hudElementAdder.accept(IModuleHelper.INSTANCE.hudElement(ICON, OxygenCanOverlay.INFINITY_TEXT, HUDColor.REGULAR));
		}
		else
		{
			hudElementAdder.accept(IModuleHelper.INSTANCE.hudElementPercent(ICON, ratio));
		}

	}

	public long getMaxProduceRate(IModule<ModuleOxygenProofUnit> module)
	{
		return (long) Math.pow(2L, module.getInstalledCount() - 1);
	}

	public long getEnergyUsingProduce()
	{
		return this.energyUsingProduce;
	}

}
