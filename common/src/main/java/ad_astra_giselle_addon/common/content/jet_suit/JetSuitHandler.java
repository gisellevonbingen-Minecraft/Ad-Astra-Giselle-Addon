package ad_astra_giselle_addon.common.content.jet_suit;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import ad_astra_giselle_addon.common.AdAstraGiselleAddon;
import ad_astra_giselle_addon.common.AddonKeyBindings;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import earth.terrarium.ad_astra.common.config.SpaceSuitConfig;
import earth.terrarium.ad_astra.common.item.armor.JetSuit;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.util.ModKeyBindings;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class JetSuitHandler
{
	public static void spawnParticle(Level level, LivingEntity entity, double pitch, double yOffset, double zOffset)
	{
		double yaw = entity.yBodyRot;
		double xRotator = Math.cos(yaw * Math.PI / 180.0) * zOffset;
		double zRotator = Math.sin(yaw * Math.PI / 180.0) * zOffset;
		double xRotator1 = Math.cos((yaw - 90) * Math.PI / 180.0) * pitch;
		double zRotator1 = Math.sin((yaw - 90) * Math.PI / 180.0) * pitch;

		double x = entity.getX() + xRotator + xRotator1;
		double y = entity.getY() + yOffset;
		double z = entity.getZ() + zRotator1 + zRotator;
		level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, x, y, z, 0.0, -0.1F, 0.0);
	}

	public static void updatePlayer(Player player)
	{
		if (!SpaceSuitConfig.enableJetSuitFlight)
		{
			return;
		}
		else if (player.isPassenger() || player.getAbilities().flying)
		{
			return;
		}
		// For client update isOnGround, Sync isOnGround with server
		else if (player.tickCount == 1)
		{
			return;
		}

		ItemStackReference jetSuit = LivingHelper.getArmorItem(player, EquipmentSlot.CHEST);

		if (!(jetSuit.getStack().getItem() instanceof JetSuit item) || !JetSuit.hasFullSet(player))
		{
			return;
		}

		if (JetSuitUtils.isPowerEnabled(jetSuit.getStack()) && JetSuitUtils.isHoverEnabled(jetSuit.getStack()) && !player.isOnGround())
		{
			boolean useEnergy = handleKey(player, jetSuit, item);

			if (useEnergy && !player.isCreative())
			{
				var energy = EnergyHooks.getItemEnergyManager(jetSuit.getStack());
				energy.extract(jetSuit, SpaceSuitConfig.jetSuitEnergyPerTick, false);
			}

		}

		if (player instanceof ServerPlayer serverPlayer)
		{
			updateFallFying(serverPlayer, item);
		}

	}

	private static void updateFallFying(ServerPlayer player, JetSuit item)
	{
		boolean isFallFlying = ((IJetSuitAccessor) item).ad_astra_giselle_addon$isFallFlying();

		if (player.isFallFlying() != isFallFlying)
		{
			if (isFallFlying)
			{
				player.startFallFlying();
			}
			else
			{
				player.stopFallFlying();
			}

		}

		if (isFallFlying)
		{
			ModUtils.sendUpdatePacket(player);
		}

	}

	public static boolean handleKey(Player player, ItemStackReference jetSuit, JetSuit item)
	{
		boolean shiftKeyDown = player.isShiftKeyDown();
		boolean jumpKeyDown = ModKeyBindings.jumpKeyDown(player);
		boolean hasEnergy = EnergyHooks.getItemEnergyManager(jetSuit.getStack()).getStoredEnergy() > 0;

		if (shiftKeyDown) // Hover Down
		{
			if (jumpKeyDown) // Hovering, Cancel Fly Upward
			{
				JetSuitUtils.hoverFlying(player, jetSuit.getStack(), 0.0D);
				return false;
			}
			else // Hover Down
			{
				JetSuitUtils.hoverFlying(player, jetSuit.getStack(), -SpaceSuitConfig.jetSuitUpwardsSpeed);
				return true;
			}

		}
		else if (jumpKeyDown) // Fly Upward
		{
			// Energy used in item.flyUpward
			return false;
		}
		else if (hasEnergy)
		{
			if (ModKeyBindings.sprintKeyDown(player)) // Fall Flying
			{
				if (!player.getLevel().isClientSide())
				{
					item.fallFly(player, jetSuit);
					item.setFallFlying(true);
					item.setEmitParticles(true);
				}

				// Energy used in item.fallFly
				return false;
			}
			else // Hovering
			{
				JetSuitUtils.hoverFlying(player, jetSuit.getStack(), 0.0D);
				return true;
			}

		}
		else
		{
			return false;
		}

	}

	public static void toggleAndSendMessage(Player player, AddonKeyBindings.Key key)
	{
		ItemStack jetSuit = JetSuitUtils.getJetSuit(player).orElse(null);

		if (jetSuit != null)
		{
			switch (key)
			{
				case JET_SUIT_POWER:
					toggleAndSendMessage(player, jetSuit, AdAstraGiselleAddon.tl("text", "jet_suit_power"), JetSuitUtils::isPowerEnabled, JetSuitUtils::setPowerEnabled);
					break;
				case JET_SUIT_HOVER:
					toggleAndSendMessage(player, jetSuit, AdAstraGiselleAddon.tl("text", "jet_suit_hover"), JetSuitUtils::isHoverEnabled, JetSuitUtils::setHoverEnabled);
					break;
			}

		}

	}

	public static void toggleAndSendMessage(Player player, ItemStack jetSuit, String component, Function<ItemStack, Boolean> getter, BiConsumer<ItemStack, Boolean> setter)
	{
		boolean next = !getter.apply(jetSuit);
		setter.accept(jetSuit, next);

		player.displayClientMessage(Component.translatable(component, getValueComponent(next)), true);
	}

	public static void addTooltip(ItemStack item, TooltipFlag flags, List<Component> lines)
	{
		if (item.is(ModItems.JET_SUIT.get()))
		{
			lines.add(1, Component.translatable(AdAstraGiselleAddon.tl("description", "jet_suit_power"), getValueComponent(JetSuitUtils.isPowerEnabled(item))));
			lines.add(2, Component.translatable(AdAstraGiselleAddon.tl("description", "jet_suit_hover"), getValueComponent(JetSuitUtils.isHoverEnabled(item))));
		}

	}

	public static Component getValueComponent(boolean enabled)
	{
		return Component.translatable(AdAstraGiselleAddon.tl("gui", enabled ? "enabled" : "disabled")).withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED);
	}

	private JetSuitHandler()
	{

	}

}
