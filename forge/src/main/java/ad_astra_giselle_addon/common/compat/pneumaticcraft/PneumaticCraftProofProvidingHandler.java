package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import org.jetbrains.annotations.NotNull;

import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.OxygenProofCommonHandler;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.FluidUtils2;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerItem;
import me.desht.pneumaticcraft.common.item.PneumaticArmorItem;
import me.desht.pneumaticcraft.common.pneumatic_armor.CommonArmorHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PneumaticCraftProofProvidingHandler
{
	@SubscribeEvent
	public void onLivingTick(LivingTickEvent e)
	{
		LivingEntity living = e.getEntity();

		if (living.level().isClientSide())
		{
			return;
		}

		int airSupply = living.getAirSupply();
		int maxAirSupply = living.getMaxAirSupply();
		int airDuration = ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;

		if (maxAirSupply - airSupply >= airDuration)
		{
			OxygenProofCommonHandler upgradeHandler = AddonCommonUpgradeHandlers.OXYGEN_PROOF;
			int airUsing = AddonPneumaticCraftConfig.OXYGEN_PROOF_AIR_USING;
			long oxygenUsing = ProofAbstractUtils.OXYGEN_PROOF_USING;

			if (this.useAirAndOxygen(living, upgradeHandler, airUsing, oxygenUsing, false))
			{
				living.setAirSupply(airSupply + airDuration);
			}

		}

	}

	public int onOxygenProof(LivingEntity living)
	{
		OxygenProofCommonHandler upgradeHandler = AddonCommonUpgradeHandlers.OXYGEN_PROOF;
		int airUsing = AddonPneumaticCraftConfig.OXYGEN_PROOF_AIR_USING;
		long oxygenUsing = ProofAbstractUtils.OXYGEN_PROOF_USING;
		return this.useAirAndOxygen(living, upgradeHandler, airUsing, oxygenUsing, false) ? ProofAbstractUtils.OXYGEN_PROOF_INTERVAL : 0;
	}

	public int onHotTemperatureProof(LivingEntity living)
	{
		IArmorUpgradeHandler<?> upgradeHandler = AddonCommonUpgradeHandlers.HOT_TEMPERATURE_PROOF;
		int airUsing = AddonPneumaticCraftConfig.HOT_TEMPERATURE_PROOF_AIR_USING;
		return this.useAir(living, upgradeHandler, airUsing, false) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
	}

	public int onAcidRainProof(LivingEntity living)
	{
		IArmorUpgradeHandler<?> upgradeHandler = AddonCommonUpgradeHandlers.ACID_RAIN_PROOF;
		int airUsing = AddonPneumaticCraftConfig.ACID_RAIN_PROOF_AIR_USING;
		return this.useAir(living, upgradeHandler, airUsing, false) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
	}

	public int onGravityProof(LivingEntity living)
	{
		IArmorUpgradeHandler<?> upgradeHandler = AddonCommonUpgradeHandlers.GRAVITY_PROOF;
		int airUsing = AddonPneumaticCraftConfig.GRAVITY_PROOF_AIR_USING;
		return this.useAir(living, upgradeHandler, airUsing, false) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
	}

	public boolean useAirAndOxygen(LivingEntity living, OxygenProofCommonHandler upgradeHandler, int airUsing, long oxygenUsing, boolean simulate)
	{
		if (this.useAir(living, upgradeHandler, airUsing, true))
		{
			if (LivingHelper.isPlayingMode(living))
			{
				IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractable(living, oxygenUsing);

				if (oxygenCharger != null)
				{
					if (!simulate)
					{
						this.useAir(living, upgradeHandler, airUsing, false);

						FluidContainer fluidContainer = oxygenCharger.getFluidContainer();
						FluidUtils2.extractFluid(fluidContainer, FluidPredicates::isOxygen, oxygenUsing, false);
					}

					return true;
				}

			}
			else
			{
				return true;
			}

		}

		return false;
	}

	public boolean useAir(LivingEntity living, IArmorUpgradeHandler<?> upgradeHandler, int airUsing, boolean simulate)
	{
		ItemStack stack = getUpgradeUsablePneumaticArmorItem(living, upgradeHandler);

		if (stack.isEmpty())
		{
			return false;
		}

		IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);
		return airHandler != null && this.useAir(living, airHandler, airUsing, simulate);
	}

	public static @NotNull ItemStack getUpgradeUsablePneumaticArmorItem(LivingEntity living, IArmorUpgradeHandler<?> upgradeHandler)
	{
		if (living instanceof Player player)
		{
			CommonArmorHandler commonHandler = CommonArmorHandler.getHandlerForPlayer(player);
			ItemStack stack = living.getItemBySlot(upgradeHandler.getEquipmentSlot());

			if (commonHandler.upgradeUsable(upgradeHandler, true) && stack.getItem() instanceof PneumaticArmorItem)
			{
				return stack;
			}

		}

		return ItemStack.EMPTY;
	}

	public boolean useAir(LivingEntity living, IAirHandlerItem airHandler, int airUsing, boolean simulate)
	{
		return !LivingHelper.isPlayingMode(living) || this.useAir(airHandler, airUsing, simulate);
	}

	public boolean useAir(IAirHandler airHandler, int airUsing, boolean simulate)
	{
		if (airHandler.getAir() >= airUsing)
		{
			if (!simulate)
			{
				airHandler.addAir(-airUsing);
			}

			return true;
		}

		return false;
	}

	public PneumaticCraftProofProvidingHandler()
	{

	}

}
