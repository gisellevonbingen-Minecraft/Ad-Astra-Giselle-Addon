package ad_astra_giselle_addon.common.content.proof;

import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;

import ad_astra_giselle_addon.common.enchantment.EnchantmentHelper2;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;

public abstract class ProofEnchantmentFunction implements ProofFunction
{
	@Override
	public int provide(Entity entity)
	{
		if (entity instanceof Player living)
		{
			@NotNull
			Pair<EquipmentSlot, Integer> pair = EnchantmentHelper2.getEnchantmentItemAndLevel(living.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(this.getEnchantment()), living);
			EquipmentSlot slot = pair.getFirst();
			int enchantLevel = pair.getSecond();

			if (slot == null || enchantLevel == 0)
			{
				return 0;
			}
			else if (LivingHelper.isPlayingMode(living))
			{
				StorageSlotContext enchantedItem = LivingHelper.getEquipmentSlot(living, slot);
				ItemUsableResource resource = ItemUsableResource.first(enchantedItem);

				if (resource != null && this.consume(living, slot, enchantedItem, resource, true))
				{
					this.consume(living, slot, enchantedItem, resource, false);
					return this.getProofDuration(resource);
				}

			}
			else
			{
				return ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
			}

		}

		return 0;
	}

	public boolean consume(Player living, EquipmentSlot slot, StorageSlotContext enchantedItem, ItemUsableResource resource, boolean simulate)
	{
		long extracting = this.getResourceUsingAmount(resource);
		long extracted = resource.extract(living, slot, enchantedItem, extracting, simulate);
		return extracted >= extracting;
	}

	public abstract ResourceKey<Enchantment> getEnchantment();

	public abstract long getResourceUsingAmount(ItemUsableResource resource);

	public abstract int getProofDuration(ItemUsableResource resource);

}
