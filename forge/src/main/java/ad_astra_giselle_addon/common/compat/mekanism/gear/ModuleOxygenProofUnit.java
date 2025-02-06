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
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IHUDElement.HUDColor;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registries.MekanismGases;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.FluidInDetails;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

public class ModuleOxygenProofUnit implements ICustomModule<ModuleOxygenProofUnit>
{
	public static final ResourceLocation ICON = AdAstraGiselleAddon.rl(MekanismUtils.ResourceType.GUI_HUD.getPrefix() + "space_breathing_unit.png");

	private FloatingLong energyUsingProduce;

	public ModuleOxygenProofUnit()
	{

	}

	@Override
	public void init(IModule<ModuleOxygenProofUnit> module, ModuleConfigItemCreator configItemCreator)
	{
		ICustomModule.super.init(module, configItemCreator);

		this.energyUsingProduce = FloatingLong.create(AddonMekanismConfig.MODULES_OXYGEN_PROOF_ENERGY_USING_PRODUCE);
	}

	@Override
	public void tickServer(IModule<ModuleOxygenProofUnit> module, Player player)
	{
		ICustomModule.super.tickServer(module, player);

		this.produceOxygen(module, player);
	}

	private void produceOxygen(IModule<ModuleOxygenProofUnit> module, Player player)
	{
		long productionRate = this.getProduceRate(module, player);

		if (productionRate == 0)
		{
			return;
		}

		FloatingLong energyUsing = this.getEnergyUsingProduce();
		productionRate = Math.min(productionRate, module.getContainerEnergy().divideToInt(energyUsing));
		long productionRateFirst = productionRate;

		ItemStack handStack = player.getItemBySlot(EquipmentSlot.MAINHAND);
		IGasHandler handCapability = handStack.getCapability(Capabilities.GAS_HANDLER).orElse(null);

		if (handCapability != null)
		{
			GasStack remain = handCapability.insertChemical(MekanismGases.OXYGEN.getStack(productionRate), Action.EXECUTE);
			productionRate = remain.getAmount();
		}

		productionRate = OxygenStorageUtils.insert(player, productionRate);

		long oxygenUsed = productionRateFirst - productionRate;
		FloatingLong multiply = energyUsing.multiply(oxygenUsed);

		if (!player.level().isClientSide())
		{
			module.useEnergy(player, multiply);
		}

		int airSupply = player.getAirSupply();
		int airFill = (int) Math.min(productionRateFirst, player.getMaxAirSupply() - airSupply);
		player.setAirSupply(airSupply + airFill);
	}

	public long getProduceRate(IModule<ModuleOxygenProofUnit> module, Player player)
	{
		float eyeHeight = player.getEyeHeight();
		Map<FluidType, FluidInDetails> fluidsIn = MekanismUtils.getFluidsIn(player, bb ->
		{
			double centerX = (bb.minX + bb.maxX) / 2;
			double centerZ = (bb.minZ + bb.maxZ) / 2;
			return new AABB(centerX, Math.min(bb.minY + eyeHeight - 0.27, bb.maxY), centerZ, centerX, Math.min(bb.minY + eyeHeight - 0.14, bb.maxY), centerZ);
		});
		if (fluidsIn.entrySet().stream().anyMatch(entry -> entry.getKey() == ForgeMod.WATER_TYPE.get() && entry.getValue().getMaxHeight() >= 0.11))
		{
			return this.getMaxProduceRate(module);
		}
		else if (player.isInWaterOrRain())
		{
			return this.getMaxProduceRate(module) / 2L;
		}

		return 0L;
	}

	public boolean useResources(IModule<ModuleOxygenProofUnit> module, LivingEntity living, boolean simulate)
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
	public void addHUDElements(IModule<ModuleOxygenProofUnit> module, Player player, Consumer<IHUDElement> hudElementAdder)
	{
		ICustomModule.super.addHUDElements(module, player, hudElementAdder);

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

	public FloatingLong getEnergyUsingProduce()
	{
		return this.energyUsingProduce;
	}

}
