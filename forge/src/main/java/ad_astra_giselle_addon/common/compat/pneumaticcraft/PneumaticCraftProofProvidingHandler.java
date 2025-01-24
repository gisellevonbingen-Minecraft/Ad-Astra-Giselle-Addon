package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import ad_astra_giselle_addon.common.compat.pneumaticcraft.pneumatic_armor.handlers.OxygenProofCommonHandler;
import ad_astra_giselle_addon.common.content.oxygen.IOxygenStorage;
import ad_astra_giselle_addon.common.content.oxygen.OxygenStorageUtils;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerItem;
import me.desht.pneumaticcraft.common.item.PneumaticArmorItem;
import me.desht.pneumaticcraft.common.pneumatic_armor.CommonArmorHandler;
import me.desht.pneumaticcraft.common.upgrades.ModUpgrades;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
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

		if (living.canDrownInFluidType(living.getEyeInFluidType()) && !MobEffectUtil.hasWaterBreathing(living))
		{
			int airSupply = living.getAirSupply();
			int maxAirSupply = living.getMaxAirSupply();
			int airDuration = ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;

			if (maxAirSupply - airSupply >= airDuration)
			{
				OxygenProofCommonHandler upgradeHandler = AddonCommonUpgradeHandlers.OXYGEN_PROOF;
				int airUsing = AddonPneumaticCraftConfig.OXYGEN_PROOF_AIR_USING;

				if (this.useAir(living, upgradeHandler, airUsing, false))
				{
					OxygenStorageUtils.insert(living, airDuration);
					living.setAirSupply(airSupply + airDuration);
				}

			}

		}

	}

	public int onOxygenProof(Entity entity)
	{
		if (entity instanceof LivingEntity living)
		{
			IArmorUpgradeHandler<?> upgradeHandler = AddonCommonUpgradeHandlers.OXYGEN_PROOF;
			long oxygenUsing = ProofAbstractUtils.OXYGEN_PROOF_USING;
			return this.useAir(living, upgradeHandler, 0, false) && this.useOxygen(living, upgradeHandler, oxygenUsing, false) ? ProofAbstractUtils.OXYGEN_PROOF_INTERVAL : 0;
		}
		else
		{
			return 0;
		}

	}

	public int onHotTemperatureProof(Entity entity)
	{
		if (entity instanceof LivingEntity living)
		{
			IArmorUpgradeHandler<?> upgradeHandler = AddonCommonUpgradeHandlers.HOT_TEMPERATURE_PROOF;
			int airUsing = AddonPneumaticCraftConfig.HOT_TEMPERATURE_PROOF_AIR_USING;
			return this.useAir(living, upgradeHandler, airUsing, false) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
		}
		else
		{
			return 0;
		}

	}

	public int onAcidRainProof(Entity entity)
	{
		if (entity instanceof LivingEntity living)
		{
			IArmorUpgradeHandler<?> upgradeHandler = AddonCommonUpgradeHandlers.ACID_RAIN_PROOF;
			int airUsing = AddonPneumaticCraftConfig.ACID_RAIN_PROOF_AIR_USING;
			return this.useAir(living, upgradeHandler, airUsing, false) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
		}
		else
		{
			return 0;
		}

	}

	public int onGravityProof(Entity entity)
	{
		if (entity instanceof LivingEntity living)
		{
			IArmorUpgradeHandler<?> upgradeHandler = AddonCommonUpgradeHandlers.GRAVITY_PROOF;
			int airUsing = AddonPneumaticCraftConfig.GRAVITY_PROOF_AIR_USING;
			return this.useAir(living, upgradeHandler, airUsing, false) ? ProofAbstractUtils.GENERAL_PROOF_INTERVAL : 0;
		}
		else
		{
			return 0;
		}

	}

	public boolean skipUse(LivingEntity living, IArmorUpgradeHandler<?> upgradeHandler)
	{
		if (!LivingHelper.isPlayingMode(living))
		{
			return true;
		}
		else if (living instanceof Player player)
		{
			CommonArmorHandler commonHandler = CommonArmorHandler.getHandlerForPlayer(player);
			ItemStack stack = living.getItemBySlot(upgradeHandler.getEquipmentSlot());

			if (commonHandler.upgradeUsable(upgradeHandler, true) && stack.getItem() instanceof PneumaticArmorItem)
			{
				if (commonHandler.getUpgradeCount(upgradeHandler.getEquipmentSlot(), ModUpgrades.CREATIVE.get()) > 0)
				{
					return true;
				}

			}

		}

		return false;
	}

	public boolean useOxygen(LivingEntity living, IArmorUpgradeHandler<?> upgradeHandler, long oxygenUsing, boolean simulate)
	{
		if (this.skipUse(living, upgradeHandler))
		{
			return true;
		}

		IOxygenStorage oxygenStorage = OxygenStorageUtils.firstExtractable(living, oxygenUsing);

		if (oxygenStorage != null)
		{
			if (!simulate)
			{
				oxygenStorage.extractOxygen(living, oxygenUsing, false);
			}

			return true;
		}

		return false;
	}

	public IAirHandlerItem getUsableAirHandler(LivingEntity living, IArmorUpgradeHandler<?> upgradeHandler)
	{
		if (living instanceof Player player)
		{
			CommonArmorHandler commonHandler = CommonArmorHandler.getHandlerForPlayer(player);
			ItemStack stack = living.getItemBySlot(upgradeHandler.getEquipmentSlot());

			if (commonHandler.upgradeUsable(upgradeHandler, true) && stack.getItem() instanceof PneumaticArmorItem)
			{
				return stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);
			}

		}

		return null;
	}

	public boolean useAir(LivingEntity living, IArmorUpgradeHandler<?> upgradeHandler, int airUsing, boolean simulate)
	{
		IAirHandlerItem airHandler = this.getUsableAirHandler(living, upgradeHandler);

		if (airHandler == null)
		{
			return false;
		}
		else if (this.skipUse(living, upgradeHandler))
		{
			return true;
		}

		return this.useAir(airHandler, airUsing, simulate);
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
