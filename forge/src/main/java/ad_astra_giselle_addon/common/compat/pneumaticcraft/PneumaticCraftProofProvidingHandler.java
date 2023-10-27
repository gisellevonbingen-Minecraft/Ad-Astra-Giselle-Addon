package ad_astra_giselle_addon.common.compat.pneumaticcraft;

import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import com.google.common.eventbus.Subscribe;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenCharger;
import ad_astra_giselle_addon.common.content.oxygen.OxygenChargerUtils;
import ad_astra_giselle_addon.common.content.proof.LivingGravityNormalizingProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.LivingSpaceFireProofProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.LivingSpaceOxygenProofProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.LivingVenusAcidProofProvidingEvent;
import ad_astra_giselle_addon.common.content.proof.ProofAbstractUtils;
import ad_astra_giselle_addon.common.content.proof.ProofSession;
import ad_astra_giselle_addon.common.entity.LivingHelper;
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
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PneumaticCraftProofProvidingHandler
{
	@SubscribeEvent
	public void onLivingTick(LivingTickEvent e)
	{
		if (e.getEntity() instanceof Player player && !player.level().isClientSide())
		{
			int airSupply = player.getAirSupply();
			int maxAirSupply = player.getMaxAirSupply();

			if (maxAirSupply - airSupply >= ProofAbstractUtils.OXYGEN_PROOF_INTERVAL)
			{
				ItemStack stack = getUpgradeUsablePneumaticArmorItem(player, AddonCommonUpgradeHandlers.SPACE_BREATHING);
				long oxygenUsing = ProofAbstractUtils.OXYGEN_PROOF_USING;
				IOxygenCharger oxygenCharger = OxygenChargerUtils.firstExtractable(player, oxygenUsing);

				if (!stack.isEmpty() && oxygenCharger != null)
				{
					IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);
					int airUsing = AddonPneumaticCraftConfig.SPACE_BREATHING_AIR_USING;

					if (airHandler != null && useAir(player, airHandler, airUsing, true))
					{
						if (!player.level().isClientSide() && LivingHelper.isPlayingMode(player))
						{
							UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();
							FluidHooks2.extractFluid(fluidHandler, FluidPredicates::isOxygen, oxygenUsing, false);
							useAir(player, airHandler, airUsing, false);
						}

						player.setAirSupply(airSupply + ProofAbstractUtils.OXYGEN_PROOF_INTERVAL);
					}

				}

			}

		}

	}

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

			if (airHandler != null && useAir(player, airHandler, airUsing, true))
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

							if (!living.level().isClientSide() && LivingHelper.isPlayingMode(living))
							{
								UniveralFluidHandler fluidHandler = oxygenCharger.getFluidHandler();
								FluidHooks2.extractFluid(fluidHandler, FluidPredicates::isOxygen, oxygenUsing, false);
								useAir(player, airHandler, airUsing, false);
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

	@Subscribe
	public void onLivingGravityNormalizing(LivingGravityNormalizingProvidingEvent e)
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
						return useAir(this.getLiving(), AddonCommonUpgradeHandlers.GRAVITY_NORMALIZING, AddonPneumaticCraftConfig.GRAVITY_NORMALIZING_AIR_USING);
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

		if (airHandler != null && useAir(player, airHandler, airUsing, true))
		{
			if (!player.level().isClientSide())
			{
				useAir(player, airHandler, airUsing, false);
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

	public boolean useAir(Player player, IAirHandlerItem airHandler, int airUsing, boolean simulate)
	{
		return !LivingHelper.isPlayingMode(player) || useAir(airHandler, airUsing, simulate);
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
