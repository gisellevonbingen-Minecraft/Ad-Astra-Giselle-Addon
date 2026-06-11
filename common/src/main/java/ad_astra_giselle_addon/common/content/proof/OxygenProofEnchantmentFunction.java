package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenStorage;
import ad_astra_giselle_addon.common.content.oxygen.OxygenStorageUtils;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;

public class OxygenProofEnchantmentFunction extends ProofEnchantmentFunction
{
	public static boolean consumeOxygen(Player living, long oxygenUsing, boolean simulate)
	{
		if (!LivingHelper.isPlayingMode(living))
		{
			return true;
		}

		IOxygenStorage oxygenStorage = OxygenStorageUtils.firstExtractable(living, oxygenUsing);

		if (oxygenStorage == null)
		{
			return false;
		}

		long extracted = oxygenStorage.extractOxygen(living, oxygenUsing, simulate);
		return extracted >= oxygenUsing;
	}

	@Override
	public ResourceKey<Enchantment> getEnchantment()
	{
		return AddonEnchantments.OXYGEN_PROOF;
	}

	@Override
	public boolean consume(Player living, EquipmentSlot slot, StorageSlotContext enchantedItem, ItemUsableResource resource, boolean simulate)
	{
		return consumeOxygen(living, this.getOxygenUsing(resource), simulate);
	}

	@Override
	public long getResourceUsingAmount(ItemUsableResource resource)
	{
		return 0;
	}

	@Override
	public int getProofDuration(ItemUsableResource resource)
	{
		return ProofAbstractUtils.OXYGEN_PROOF_INTERVAL;
	}

	public long getOxygenUsing(ItemUsableResource resource)
	{
		return ProofAbstractUtils.OXYGEN_PROOF_USING;
	}

}
