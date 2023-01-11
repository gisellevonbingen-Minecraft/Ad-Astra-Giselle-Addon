package ad_astra_giselle_addon.common.content.proof;

import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;

import ad_astra_giselle_addon.common.enchantment.EnchantmentEnergyStorageOrDamageable;
import ad_astra_giselle_addon.common.enchantment.EnchantmentHelper2;
import ad_astra_giselle_addon.common.entity.LivingHelper;
import ad_astra_giselle_addon.common.item.ItemStackReference;
import ad_astra_giselle_addon.common.item.ItemUsableResource;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.entity.LivingEntity;

public abstract class ProofEnchantmentSession extends ProofSession
{
	private EnchantmentEnergyStorageOrDamageable enchantment;
	private ItemStackHolder enchantedItem;
	private int enchantLevel;

	private ItemUsableResource testedUsableResource;

	public ProofEnchantmentSession(LivingEntity living, EnchantmentEnergyStorageOrDamageable enchantment)
	{
		super(living);
		this.enchantment = enchantment;

		@NotNull
		Pair<ItemStackReference, Integer> pair = EnchantmentHelper2.getEnchantmentItemAndLevel(enchantment, living);
		this.enchantedItem = pair.getFirst();
		this.enchantLevel = pair.getSecond();

		this.testedUsableResource = null;
	}

	@Override
	public boolean canProvide()
	{
		if (!super.canProvide())
		{
			return false;
		}

		ItemStackHolder enchantedItem = this.getEnchantedItem();
		int enchantLevel = this.getEnchantLevel();

		if (enchantedItem == null || enchantLevel == 0)
		{
			return false;
		}

		if (LivingHelper.isPlayingMode(this.getLiving()))
		{
			ItemUsableResource resource = ItemUsableResource.first(enchantedItem.getStack());
			this.testedUsableResource = resource;

			if (resource != null)
			{
				long using = this.getResourceUsingAmount(resource);
				long extracting = resource.extract(enchantedItem, using, true);

				if (extracting >= using)
				{
					return true;
				}

			}

			return false;
		}
		else
		{
			this.testedUsableResource = ItemUsableResource.None;
			return true;
		}

	}

	@Override
	public void onProvide()
	{
		super.onProvide();

		LivingEntity entity = this.getLiving();

		if (LivingHelper.isPlayingMode(entity))
		{
			if (!entity.getLevel().isClientSide())
			{
				ItemStackHolder enchantedItem = this.getEnchantedItem();
				ItemUsableResource resource = this.getTestedUsableResource();
				long using = this.getResourceUsingAmount(resource);
				resource.extract(enchantedItem, using, false);
			}

		}

	}

	public abstract long getResourceUsingAmount(ItemUsableResource resource);

	public abstract int getProofDuration(ItemUsableResource resource);

	@Override
	public int getProofDuration()
	{
		ItemUsableResource resource = this.getTestedUsableResource();

		if (resource == ItemUsableResource.None)
		{
			return ProofAbstractUtils.GENERAL_PROOF_INTERVAL;
		}
		else
		{
			return this.getProofDuration(resource);
		}

	}

	public EnchantmentEnergyStorageOrDamageable getEnchantment()
	{
		return this.enchantment;
	}

	public ItemStackHolder getEnchantedItem()
	{
		return this.enchantedItem;
	}

	public int getEnchantLevel()
	{
		return this.enchantLevel;
	}

	public ItemUsableResource getTestedUsableResource()
	{
		return this.testedUsableResource;
	}

}
