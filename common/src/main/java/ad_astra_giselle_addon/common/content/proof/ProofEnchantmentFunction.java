package ad_astra_giselle_addon.common.content.proof;

import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;

import ad_astra_giselle_addon.common.enchantment.EnchantmentHelper2;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;

public abstract class ProofEnchantmentFunction implements ProofFunction
{
	@Override
	public int provide(LivingEntity living)
	{
		@NotNull
		Pair<ItemStackReference, Integer> pair = EnchantmentHelper2.getEnchantmentItemAndLevel(this.getEnchantment(), living);
		ItemStackReference enchantedItem = pair.getFirst();
		int enchantLevel = pair.getSecond();

		if (enchantedItem == null || enchantLevel == 0)
		{
			return 0;
		}
		else if (LivingHelper.isPlayingMode(living))
		{
			ItemUsableResource resource = ItemUsableResource.first(enchantedItem.getStack());

			if (resource != null && this.consume(living, enchantedItem, resource, true))
			{
				this.consume(living, enchantedItem, resource, false);
				return this.getProofDuration(resource);
			}

			return 0;
		}
		else
		{
			return ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
		}

	}

	public boolean consume(LivingEntity living, ItemStackReference enchantedItem, ItemUsableResource resource, boolean simulate)
	{
		long extracting = this.getResourceUsingAmount(resource);
		long extracted = resource.extract(enchantedItem, extracting, simulate);
		return extracted >= extracting;
	}

	public abstract Enchantment getEnchantment();

	public abstract long getResourceUsingAmount(ItemUsableResource resource);

	public abstract int getProofDuration(ItemUsableResource resource);

}
