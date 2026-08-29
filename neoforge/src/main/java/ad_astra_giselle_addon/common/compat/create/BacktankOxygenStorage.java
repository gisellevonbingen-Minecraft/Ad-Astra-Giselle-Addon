package ad_astra_giselle_addon.common.compat.create;

import org.jetbrains.annotations.Nullable;

import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.content.equipment.armor.BacktankUtil;

import ad_astra_giselle_addon.common.content.oxygen.IOxygenStorage;
import ad_astra_giselle_addon.common.item.StorageSlotContext;
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;

public class BacktankOxygenStorage implements IOxygenStorage
{
	public static IOxygenStorage getOxygenStroage(StorageSlotContext slot)
	{
		if (slot.getItem() instanceof BacktankItem item)
		{
			return new BacktankOxygenStorage(slot, item.getMaterial());
		}

		return null;
	}

	private final StorageSlotContext slot;
	private final Holder<ArmorMaterial> material;

	public BacktankOxygenStorage(StorageSlotContext slot, Holder<ArmorMaterial> material)
	{
		this.slot = slot;
		this.material = material;
	}

	@Override
	public long extractOxygen(@Nullable LivingEntity living, long amount, boolean simulate)
	{
		ItemStack stack = this.getSlot().getItemStack();
		int air = BacktankUtil.getAir(stack);
		long neededAir = this.toAir(amount);
		int usingAir =  (int)Math.min(air, neededAir);

		if (!simulate)
		{
			BacktankUtil.consumeAir(living, stack, usingAir);
			this.getSlot().update(stack);
		}

		return this.toAmount(usingAir);
	}

	@Override
	public long getOxygenAmount()
	{
		int air = BacktankUtil.getAir(this.getSlot().getItemStack());
		return this.toAmount(air);
	}

	@Override
	public long getOxygenCapacity()
	{
		int air = BacktankUtil.maxAir(this.getSlot().getItemStack());
		return this.toAmount(air);
	}

	private long toAir(long amount)
	{
		return FluidAmounts.toMillibuckets(amount);
	}

	private long toAmount(long air)
	{
		return air * FluidAmounts.toPlatformAmount(1);
	}

	@Override
	public boolean canUseOnCold()
	{
		return true;
	}

	@Override
	public boolean canUseOnHot()
	{
		return this.getMaterial() == ArmorMaterials.NETHERITE;
	}

	public StorageSlotContext getSlot()
	{
		return this.slot;
	}

	public Holder<ArmorMaterial> getMaterial()
	{
		return this.material;
	}

}
