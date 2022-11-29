package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import javax.annotation.Nonnull;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.proof.LivingSpaceFireProofEvent;
import ad_astra_giselle_addon.common.content.proof.LivingSpaceOxygenProofEvent;
import ad_astra_giselle_addon.common.content.proof.LivingVenusAcidProofEvent;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerItem;
import me.desht.pneumaticcraft.common.item.PneumaticArmorItem;
import me.desht.pneumaticcraft.common.pneumatic_armor.CommonArmorHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PneumaticCraftEventHandler
{
	@SubscribeEvent
	public static void onLivingSpaceOxygenProof(LivingSpaceOxygenProofEvent e)
	{
		LivingEntity entity = e.getEntity();

		if (!(entity instanceof Player player))
		{
			return;
		}

		ItemStack stack = getUpgradeUsablePneumaticArmorItem(player, AddonCommonUpgradeHandlers.SPACE_BREATHING);

		if (stack.isEmpty())
		{
			return;
		}

		IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);
		int airUsing = AddonPneumaticCraftConfig.SPACE_BREATHING_AIR_USING;

		if (airHandler != null && useAir(airHandler, airUsing, true))
		{
			int oxygenUsing = 1;
			IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractable(entity, oxygenUsing, stack);

			if (oxygenCharger != null)
			{
				if (!entity.getLevel().isClientSide())
				{
					UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();
					FluidHooks2.extractFluid(fluidHandler, FluidPredicates::isOxygen, oxygenUsing, false);
					useAir(airHandler, airUsing, false);
				}

				e.setProofDuration(AddonPneumaticCraftConfig.SPACE_BREATHING_OXYGEN_DURATION);
			}

		}

	}

	@SubscribeEvent
	public static void onLivingSpaceFireProof(LivingSpaceFireProofEvent e)
	{
		boolean cancelled = tryCancel(e, AddonCommonUpgradeHandlers.SPACE_FIRE_PROOF, AddonPneumaticCraftConfig.SPACE_FIRE_PROOF_AIR_USING);

		if (cancelled)
		{
			e.setProofDuration(1);
		}

	}

	@SubscribeEvent
	public static void onLivingVenusAcidProof(LivingVenusAcidProofEvent e)
	{
		boolean cancelled = tryCancel(e, AddonCommonUpgradeHandlers.VENUS_ACID_PROOF, AddonPneumaticCraftConfig.VENUS_ACID_PROOF_AIR_USING);

		if (cancelled)
		{
			e.setProofDuration(1);
		}

	}

	public static boolean tryCancel(EntityEvent e, IArmorUpgradeHandler<?> upgradeHandler, int airUsing)
	{
		if (!e.isCancelable() || e.isCanceled() || !(e.getEntity() instanceof Player player))
		{
			return false;
		}

		ItemStack stack = getUpgradeUsablePneumaticArmorItem(player, upgradeHandler);

		if (stack.isEmpty())
		{
			return false;
		}

		IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);

		if (airHandler != null && useAir(airHandler, airUsing, true))
		{
			if (!player.getLevel().isClientSide())
			{
				useAir(airHandler, airUsing, false);
			}

			e.setCanceled(true);
			return true;
		}
		else
		{
			return false;
		}

	}

	public static @Nonnull ItemStack getUpgradeUsablePneumaticArmorItem(Player player, IArmorUpgradeHandler<?> upgradeHandler)
	{
		ItemStack stack = player.getItemBySlot(upgradeHandler.getEquipmentSlot());

		if (stack.getItem() instanceof PneumaticArmorItem)
		{
			CommonArmorHandler commonHandler = CommonArmorHandler.getHandlerForPlayer(player);

			if (commonHandler.upgradeUsable(upgradeHandler, true))
			{
				return stack;
			}

		}

		return ItemStack.EMPTY;
	}

	public static boolean useAir(IAirHandler airHandler, int airUsing, boolean simulate)
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

}
