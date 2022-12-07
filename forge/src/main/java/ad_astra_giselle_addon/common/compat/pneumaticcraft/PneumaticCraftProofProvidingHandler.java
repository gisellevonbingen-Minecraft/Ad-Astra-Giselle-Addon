package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.google.common.eventbus.Subscribe;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.proof.LivingSpaceFireProofProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.LivingSpaceOxygenProofProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.LivingVenusAcidProofProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.content.proof.ProofSession;
import ad_astra_giselle_addon.common.fluid.FluidHooks2;
import ad_astra_giselle_addon.common.fluid.FluidPredicates;
import ad_astra_giselle_addon.common.fluid.UniveralFluidHandler;
import ad_astra_giselle_addon.common.util.LivingHelper;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerItem;
import me.desht.pneumaticcraft.common.item.PneumaticArmorItem;
import me.desht.pneumaticcraft.common.pneumatic_armor.CommonArmorHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PneumaticCraftProofProvidingHandler
{
	@Subscribe
	public void onLivingSpaceOxygenProof(LivingSpaceOxygenProofProvidingEvent e)
	{
		e.add(l ->
		{
			if (!(l instanceof Player player))
			{
				return null;
			}

			ItemStack stack = getUpgradeUsablePneumaticArmorItem(player, AddonCommonUpgradeHandlers.SPACE_BREATHING);

			if (stack.isEmpty())
			{
				return null;
			}

			IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);
			int airUsing = AddonPneumaticCraftConfig.SPACE_BREATHING_AIR_USING;

			if (airHandler != null && (!LivingHelper.isPlayingMode(player) || PneumaticCraftProofProvidingHandler.useAir(airHandler, airUsing, true)))
			{
				long oxygenUsing = ProofAbstractUtils.OXYGEN_PROOF_USING;
				IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractable(player, oxygenUsing);

				if (oxygenCharger != null)
				{
					return new ProofSession(l)
					{
						@Override
						public void onProvide()
						{
							LivingEntity living = this.getLiving();

							if (!living.getLevel().isClientSide() && LivingHelper.isPlayingMode(living))
							{
								UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();
								FluidHooks2.extractFluid(fluidHandler, FluidPredicates::isOxygen, oxygenUsing, false);
								useAir(airHandler, airUsing, false);
							}

						}

						@Override
						public int getProofDuration()
						{
							return ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;
						}
					};
				}
			}

			return null;
		});

	}

	@Subscribe
	public void onLivingSpaceFireProof(LivingSpaceFireProofProvidingEvent e)
	{
		e.add(new Function<LivingEntity, ProofSession>()
		{
			@Override
			public ProofSession apply(LivingEntity living)
			{
				return new ProofSession(living)
				{
					@Override
					public boolean canProvide()
					{
						return useAir(this.getLiving(), AddonCommonUpgradeHandlers.SPACE_FIRE_PROOF, AddonPneumaticCraftConfig.SPACE_FIRE_PROOF_AIR_USING);
					}

					@Override
					public int getProofDuration()
					{
						return ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
					}
				};
			}
		});
	}

	@Subscribe
	public void onLivingVenusAcidProof(LivingVenusAcidProofProvidingEvent e)
	{
		e.add(new Function<LivingEntity, ProofSession>()
		{
			@Override
			public ProofSession apply(LivingEntity living)
			{
				return new ProofSession(living)
				{
					@Override
					public boolean canProvide()
					{
						return useAir(this.getLiving(), AddonCommonUpgradeHandlers.ACID_RAIN_PROOF, AddonPneumaticCraftConfig.ACID_RAIN_PROOF_AIR_USING);
					}

					@Override
					public int getProofDuration()
					{
						return ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
					}
				};
			}
		});
	}

	public boolean useAir(LivingEntity living, IArmorUpgradeHandler<?> upgradeHandler, int airUsing)
	{
		if (!(living instanceof Player player))
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

			return true;
		}
		else
		{
			return false;
		}

	}

	public static @NotNull ItemStack getUpgradeUsablePneumaticArmorItem(Player player, IArmorUpgradeHandler<?> upgradeHandler)
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

	public PneumaticCraftProofProvidingHandler()
	{

	}

}
