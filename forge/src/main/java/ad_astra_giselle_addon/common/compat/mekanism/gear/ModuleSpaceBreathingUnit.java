package ad_astra_giselle_addon.common.compat.mekanism.gear;

import java.util.Map;
import java.util.function.Consumer;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.compat.mekanism.AddonMekanismConfig;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import mekanism.api.Action;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
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

public class ModuleSpaceBreathingUnit implements ICustomModule<ModuleSpaceBreathingUnit>
{
	public static final ResourceLocation ICON = AdAstraGiselleAddon.rl(MekanismUtils.ResourceType.GUI_HUD.getPrefix() + "space_breathing_unit.png");

	private int oxygenDuration = 0;
	private FloatingLong energyUsingProvide;
	private FloatingLong energyUsingProduce;

	public ModuleSpaceBreathingUnit()
	{

	}

	@Override
	public void init(IModule<ModuleSpaceBreathingUnit> module, ModuleConfigItemCreator configItemCreator)
	{
		ICustomModule.super.init(module, configItemCreator);

		this.oxygenDuration = AddonMekanismConfig.MODULES_SPACE_BREATHING_OXYGEN_DURATION;
		this.energyUsingProvide = FloatingLong.create(AddonMekanismConfig.MODULES_SPACE_BREATHING_ENERGY_USING_PROVIDE);
		this.energyUsingProduce = FloatingLong.create(AddonMekanismConfig.MODULES_SPACE_BREATHING_ENERGY_USING_PRODUCE);
	}

	@Override
	public void tickServer(IModule<ModuleSpaceBreathingUnit> module, Player player)
	{
		ICustomModule.super.tickServer(module, player);

		this.produceOxygen(module, player);
	}

	private void produceOxygen(IModule<ModuleSpaceBreathingUnit> module, Player player)
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

		long oxygenUsed = productionRateFirst - productionRate;
		FloatingLong multiply = energyUsing.multiply(oxygenUsed);

		if (!player.getLevel().isClientSide())
		{
			module.useEnergy(player, multiply);
		}

		int airSupply = player.getAirSupply();
		int airFill = (int) Math.min(productionRateFirst, player.getMaxAirSupply() - airSupply);
		player.setAirSupply(airSupply + airFill);
	}

	public long getProduceRate(IModule<ModuleSpaceBreathingUnit> module, Player player)
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

	public boolean useResources(IModule<ModuleSpaceBreathingUnit> module, LivingEntity living, boolean simulate)
	{
		int oxygenUsing = 1;
		IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractable(living, oxygenUsing, module.getContainer());

		if (oxygenCharger != null)
		{
			UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();

			if (FluidHooks2.extractFluid(fluidHandler, FluidPredicates::isOxygen, oxygenUsing, true).getFluidAmount() >= oxygenUsing)
			{
				FloatingLong energyUsing = this.getEnergyUsingProvide();

				if (module.canUseEnergy(living, energyUsing))
				{
					if (!simulate && !living.getLevel().isClientSide())
					{
						FluidHooks2.extractFluid(fluidHandler, FluidPredicates::isOxygen, oxygenUsing, false);
						module.useEnergy(living, energyUsing);
					}

					return true;
				}

			}

		}

		return false;
	}

	@Override
	public void addHUDElements(IModule<ModuleSpaceBreathingUnit> module, Player player, Consumer<IHUDElement> hudElementAdder)
	{
		ICustomModule.super.addHUDElements(module, player, hudElementAdder);

		if (!module.isEnabled())
		{
			return;
		}

		double ratio = OxygenChargerUtils.getInventoryStorageRatio(player).orElse(0.0D);
		hudElementAdder.accept(MekanismAPI.getModuleHelper().hudElementPercent(ICON, ratio));
	}

	public long getMaxProduceRate(IModule<ModuleSpaceBreathingUnit> module)
	{
		return (long) Math.pow(2L, module.getInstalledCount() - 1);
	}

	public int getOxygenDuration()
	{
		return this.oxygenDuration;
	}

	public FloatingLong getEnergyUsingProvide()
	{
		return this.energyUsingProvide;
	}

	public FloatingLong getEnergyUsingProduce()
	{
		return this.energyUsingProduce;
	}

}
