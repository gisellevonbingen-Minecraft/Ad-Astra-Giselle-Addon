package ad_astra_giselle_addon.common.content.proof;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenStorage;
import ad_astra_giselle_addon.common.content.oxygen.OxygenStorageUtils;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import ad_astra_giselle_addon.common.registry.AddonEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;

public class OxygenProofEnchantmentFunction extends ProofEnchantmentFunction
{
	public static boolean consumeOxygen(LivingEntity living, long oxygenUsing, boolean simulate)
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
	public Enchantment getEnchantment()
	{
		return AddonEnchantments.OXYGEN_PROOF.get();
	}

	@Override
	public boolean consume(LivingEntity living, EquipmentSlot slot, ItemStackReference enchantedItem, ItemUsableResource resource, boolean simulate)
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
