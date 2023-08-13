package ad_astra_giselle_addon.common.content.jet_suit;

import java.util.Optional;

import ad_astra_giselle_addon.common.util.NBTUtils;
import earth.terrarium.ad_astra.common.item.armor.JetSuit;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class JetSuitUtils
{
	public static final String CAPABILITY_TAG_NAME = "JetSuitCapability";
	public static final String POWER_ENABLED_TAG_NAME = "PowerEnabled";
	public static final String HOVER_ENABLED_TAG_NAME = "HoverEnabled";

	public static Optional<ItemStack> getJetSuit(LivingEntity living)
	{
		if (living instanceof Player player)
		{
			ItemStack jetSuit = player.getItemBySlot(EquipmentSlot.CHEST);

			if (jetSuit.is(ModItems.JET_SUIT.get()))
			{
				return Optional.of(jetSuit);
			}

		}

		return Optional.empty();
	}

	public static CompoundTag getCapabilityTag(ItemStack jetSuit)
	{
		return NBTUtils.getTag(jetSuit, CAPABILITY_TAG_NAME);
	}

	public static CompoundTag getOrCreateCapabilityTag(ItemStack jetSuit)
	{
		return NBTUtils.getOrCreateTag(jetSuit, CAPABILITY_TAG_NAME);
	}

	public static boolean getFallbackableEnabled(ItemStack jetSuit, String name, boolean fallback)
	{
		CompoundTag capabilityTag = getCapabilityTag(jetSuit);
		return capabilityTag.contains(name) ? capabilityTag.getBoolean(name) : fallback;
	}

	public static boolean isPowerEnabled(ItemStack jetSuit)
	{
		return getFallbackableEnabled(jetSuit, POWER_ENABLED_TAG_NAME, true);
	}

	public static void setPowerEnabled(ItemStack jetSuit, boolean enabled)
	{
		getOrCreateCapabilityTag(jetSuit).putBoolean(POWER_ENABLED_TAG_NAME, enabled);
	}

	public static boolean isHoverEnabled(ItemStack jetSuit)
	{
		return getFallbackableEnabled(jetSuit, HOVER_ENABLED_TAG_NAME, false);
	}

	public static void setHoverEnabled(ItemStack jetSuit, boolean enabled)
	{
		getOrCreateCapabilityTag(jetSuit).putBoolean(HOVER_ENABLED_TAG_NAME, enabled);
	}

	public static void cancelFlying(Player player, ItemStack jetSuit)
	{
		if (jetSuit.getItem() instanceof JetSuit item)
		{
			item.setFallFlying(false);
			item.setEmitParticles(false);
		}

	}

	public static void hoverFlying(Player player, ItemStack jetSuit, double verticalSpeed)
	{
		if (jetSuit.getItem() instanceof JetSuit item)
		{
			Vec3 velocity = player.getDeltaMovement();
			player.fallDistance = 0.0F;
			player.setDeltaMovement(velocity.multiply(1.0D, 0.0D, 1.0D).add(0.0D, verticalSpeed, 0.0D));

			if (!player.getLevel().isClientSide())
			{
				item.setEmitParticles(true);
			}

		}

	}

	private JetSuitUtils()
	{

	}

}
