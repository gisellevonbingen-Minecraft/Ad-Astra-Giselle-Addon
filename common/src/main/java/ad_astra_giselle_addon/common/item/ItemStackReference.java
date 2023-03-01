package ad_astra_giselle_addon.common.item;

import java.util.function.Consumer;

import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.item.ItemStack;

public class ItemStackReference extends ItemStackHolder
{
	private final Consumer<ItemStack> setter;

	public ItemStackReference(ItemStack item, Consumer<ItemStack> setter)
	{
		super(item);
		this.setter = setter;
	}

	@Override
	public ItemStackReference copy()
	{
		return new ItemStackReference(this.getStack().copy(), this.setter);
	}

	@Override
	public void setStack(ItemStack stack)
	{
		super.setStack(stack);

		if (this.isDirty())
		{
			this.setter.accept(this.getStack());
		}

	}

}
